package service.impl;

import dao.CheckDao;
import dao.CheckSpecDao;
import dao.GoodsDao;
import domain.Check;
import domain.Checkspec;
import domain.Goods;
import domain.User;
import entity.CheckEntity;
import entity.CheckspecEntity;
import entity.GoodsEntity;
import exception.InvalidDataRuntimeException;
import exception.NotEnoughGoodsQuantRuntimeException;
import org.apache.log4j.Logger;
import service.CheckService;
import service.mapper.CheckMapper;
import service.mapper.CheckSpecMapper;
import service.mapper.GoodMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CheckServiceImpl implements CheckService {
    private static final Logger LOGGER = Logger.getLogger(CheckServiceImpl.class);

    private final GoodsDao goodsDao;
    private final CheckDao checkDao;
    private final CheckSpecDao checkSpecDao;
    private final GoodMapper goodMapper;
    private final CheckMapper checkMapper;
    private final CheckSpecMapper checkSpecMapper;

    public CheckServiceImpl(GoodsDao goodsDao, CheckDao checkDao, CheckSpecDao checkSpecDao,
                            GoodMapper goodMapper, CheckMapper checkMapper, CheckSpecMapper checkSpecMapper) {
        this.goodsDao = goodsDao;
        this.checkDao = checkDao;
        this.checkSpecDao = checkSpecDao;
        this.goodMapper = goodMapper;
        this.checkMapper = checkMapper;
        this.checkSpecMapper = checkSpecMapper;
    }

    @Override
    public Checkspec addCheckSpec(String name, String code, Double quant, String nds) {
        if (Objects.isNull(nds) || Integer.valueOf(nds) < 0 || Integer.valueOf(nds) > 100
                || quant < 0 || quant > 100000) {
            LOGGER.error("Data of checkspec is uncorrected");
            throw new InvalidDataRuntimeException("Data of checkspec is uncorrect");
        }

        Optional<GoodsEntity> goodsEntity = name.isEmpty()
                ? goodsDao.findGoods(Integer.valueOf(code))
                : goodsDao.findGoods(name);

        Goods existsGoods = goodMapper.goodEntityToGood(goodsEntity.orElse(null));

        if (Objects.isNull(existsGoods)) {
            return null;
        }

        if (quant > existsGoods.getQuant()) {
            LOGGER.info("Недостатня кількість товару");
            throw new NotEnoughGoodsQuantRuntimeException("Недостатня кількість товару");
        }

        Checkspec spec = new Checkspec();
        spec.setIdGood(existsGoods.getId());
        spec.setXname(existsGoods.getName());
        spec.setXcode(existsGoods.getCode());
        spec.setQuant(quant);
        spec.setPrice(existsGoods.getPrice());
        spec.setTotal(BigDecimal.valueOf(spec.getQuant()).multiply(BigDecimal.valueOf(spec.getPrice())).doubleValue());
        spec.setNds(Integer.valueOf(nds));
        spec.setNdstotal(BigDecimal.valueOf(spec.getTotal()).multiply(BigDecimal.valueOf(spec.getNds())).divide(new BigDecimal(100)).doubleValue());
        return spec;
    }

    @Override
    public void addCheck(User user, List<Checkspec> checkspecs) {
        if (Objects.isNull(user) || checkspecs.isEmpty()) {
            LOGGER.warn("Data of check is uncorrected");
            throw new InvalidDataRuntimeException("Data of check is uncorrect");
        }

        Check check = new Check();
        check.setCreator(user.getId());

        double total = checkspecs.stream().mapToDouble(Checkspec::getTotal).sum();
        check.setTotal(total);

        CheckEntity checkEntity = checkMapper.checkToCheckEntity(check);
        Long idCheck = checkDao.insert(checkEntity);
        for (Checkspec checkspec : checkspecs
        ) {
            checkspec.setIdCheck(idCheck);
            Optional<GoodsEntity> goods = goodsDao.findById(checkspec.getIdGood());
            if (goods.isPresent()) {
                goods.get().setQuant(goods.get().getQuant() - checkspec.getQuant());
                goodsDao.update(goods.get());
            }
        }

        List<CheckspecEntity> checkspecEnteties = checkspecs.stream()
                .map(checkSpecMapper::checkspecToCheckspecEntity)
                .collect(Collectors.toList());
        checkSpecDao.insertAll(checkspecEnteties);
    }

    @Override
    public List<Checkspec> findAllCheckspecByCheckId(Long checkId) {
        if (Objects.isNull(checkId) || checkId < 0) {
            LOGGER.warn("Check id is uncorrected");
            throw new InvalidDataRuntimeException("Check id is uncorrect");
        }

        List<CheckspecEntity> orderEntities = checkSpecDao.findAllByCheckId(checkId);
        for (CheckspecEntity checkspecEntity : orderEntities
        ) {
            Optional<GoodsEntity> goodsEntity = goodsDao.findById(checkspecEntity.getIdGood());
            Goods goods = goodsEntity.map(goodMapper::goodEntityToGood)
                    .orElseThrow(() -> new InvalidDataRuntimeException("Order is not exist"));
            checkspecEntity.setXcode(goods.getCode());
            checkspecEntity.setXname(goods.getName());
        }

        return orderEntities.isEmpty() ?
                Collections.emptyList() :
                orderEntities.stream()
                        .map(checkSpecMapper::checkspecEntityToCheckspec)
                        .collect(Collectors.toList());
    }

    @Override
    public Check findById(Long id) {
        if (Objects.isNull(id) || id < 0) {
            LOGGER.warn("Check id is uncorrected");
            throw new InvalidDataRuntimeException("Check id is uncorrect");
        }

        Optional<CheckEntity> check = checkDao.findById(id);
        return check.map(checkMapper::checkEntityToCheck)
                .orElseThrow(() -> new InvalidDataRuntimeException("Check id is uncorrect"));
    }

    @Override
    public void cancelCheck(Check check) {
        if (Objects.isNull(check)) {
            LOGGER.warn("Check is uncorrected");
            throw new InvalidDataRuntimeException("Check is uncorrect");
        }

        check.setCanceled(1);
        CheckEntity checkEntity = checkMapper.checkToCheckEntity(check);
        checkDao.update(checkEntity);
    }

    @Override
    public void cancelCheckSpec(List<Checkspec> checkspecs, int checkspecnum, Check check) {
        if (Objects.isNull(check) || checkspecnum < 0) {
            LOGGER.warn("Data is uncorrected");
            throw new InvalidDataRuntimeException("Data is uncorrect");
        }

        checkspecs.get(checkspecnum - 1).setCanceled(1);
        Checkspec checkspec = checkspecs.get(checkspecnum - 1);

        CheckspecEntity checkspecEntity = checkSpecMapper.checkspecToCheckspecEntity(checkspec);
        checkSpecDao.update(checkspecEntity);

        double total = checkspecs.stream()
                .filter(spec -> spec.getCanceled() == 0)
                .mapToDouble(Checkspec::getTotal).sum();
        check.setTotal(total);

        CheckEntity checkEntity = checkMapper.checkToCheckEntity(check);
        checkDao.update(checkEntity);
    }
}
