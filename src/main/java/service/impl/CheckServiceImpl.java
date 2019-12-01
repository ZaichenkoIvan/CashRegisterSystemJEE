package main.java.service.impl;

import main.java.dao.CheckDao;
import main.java.dao.CheckSpecDao;
import main.java.dao.GoodsDao;
import main.java.entity.Check;
import main.java.entity.Checkspec;
import main.java.entity.Goods;
import main.java.entity.User;
import main.java.exception.InvalidDataRuntimeException;
import main.java.exception.NotEnoughGoodsQuantRuntimeException;
import main.java.service.CheckService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CheckServiceImpl implements CheckService {
    private static final Logger LOGGER = Logger.getLogger(CheckServiceImpl.class);

    private final GoodsDao goodsDao;
    private final CheckDao checkDao;
    private final CheckSpecDao checkSpecDao;

    public CheckServiceImpl(GoodsDao goodsDao, CheckDao checkDao, CheckSpecDao checkSpecDao) {
        this.goodsDao = goodsDao;
        this.checkDao = checkDao;
        this.checkSpecDao = checkSpecDao;
    }

    @Override
    public Checkspec addCheckSpec(Integer code, Double quant, String nds) {
        if (Objects.isNull(code) || Objects.isNull(nds) || Integer.valueOf(nds) < 0 || Integer.valueOf(nds) > 100
                || quant < 0 || quant > 100000) {
            LOGGER.error("Data of checkspec is uncorrected");
            throw new InvalidDataRuntimeException("Data of checkspec is uncorrect");
        }

        Goods existsGoods = goodsDao.findGoods(code);
        if (existsGoods == null) {
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
        Long idCheck = checkDao.insert(check);
        for (Checkspec checkspec : checkspecs
        ) {
            checkspec.setIdCheck(idCheck);
            Optional<Goods> goods = goodsDao.findById(checkspec.getIdGood());
            if (goods.isPresent()) {
                goods.get().setQuant(goods.get().getQuant() - checkspec.getQuant());
                goodsDao.update(goods.get());
            }
        }
        checkSpecDao.insertAll(checkspecs);
    }

    @Override
    public List<Checkspec> findAllCheckspecByCheckId(Long checkId) {
        if (Objects.isNull(checkId) || checkId < 0) {
            LOGGER.warn("Check id is uncorrected");
            throw new InvalidDataRuntimeException("Check id is uncorrect");
        }

        return checkSpecDao.findAllByCheckId(checkId);
    }

    @Override
    public Check findById(Long id) {
        if (Objects.isNull(id) || id < 0) {
            LOGGER.warn("Check id is uncorrected");
            throw new InvalidDataRuntimeException("Check id is uncorrect");
        }

        return checkDao.findById(id).orElseThrow(() -> new InvalidDataRuntimeException("Check id is uncorrect"));
    }

    @Override
    public void cancelCheck(Check check) {
        if (Objects.isNull(check)) {
            LOGGER.warn("Check is uncorrected");
            throw new InvalidDataRuntimeException("Check is uncorrect");
        }

        check.setCanceled(1);
        checkDao.update(check);
    }

    @Override
    public void cancelCheckSpec(List<Checkspec> checkspecs, int checkspecnum, Check check) {
        if (Objects.isNull(check) || checkspecnum < 0) {
            LOGGER.warn("Data is uncorrected");
            throw new InvalidDataRuntimeException("Data is uncorrect");
        }

        checkspecs.get(checkspecnum - 1).setCanceled(1);
        checkSpecDao.update(checkspecs.get(checkspecnum - 1));
        double total = checkspecs.stream()
                .filter(spec -> spec.getCanceled() == 0)
                .mapToDouble(Checkspec::getTotal).sum();
        check.setTotal(total);
        checkDao.update(check);
    }
}
