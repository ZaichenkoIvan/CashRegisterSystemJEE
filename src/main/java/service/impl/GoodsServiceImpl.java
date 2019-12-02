package service.impl;

import dao.GoodsDao;
import domain.Goods;
import entity.GoodsEntity;
import exception.InvalidDataRuntimeException;
import service.GoodsService;
import service.mapper.GoodMapper;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GoodsServiceImpl implements GoodsService {

    private static final Logger LOGGER = Logger.getLogger(GoodsServiceImpl.class);

    private final GoodsDao goodsDao;
    private final GoodMapper goodMapper;

    public GoodsServiceImpl(GoodsDao goodsDao, GoodMapper goodMapper) {
        this.goodsDao = goodsDao;
        this.goodMapper = goodMapper;
    }

    @Override
    public Long addGoods(int code, String name, double quant, double price, String measure, String comments) {
        if (quant < 0 || price < 0 || quant > 100000 || price > 100000 || code < 0) {
            LOGGER.warn("Good data is uncorrected");
            throw new InvalidDataRuntimeException("Good data is uncorrected");
        }

        Goods goods = new Goods();
        goods.setCode(code);
        goods.setName(name);
        goods.setQuant(quant);
        goods.setPrice(price);
        goods.setMeasure(measure);
        goods.setComments(comments);
        Optional<GoodsEntity> existsGood = goodsDao.findGoods(code);

        if (existsGood.isPresent()) {
            LOGGER.warn("Товар с кодом " + code + " уже существует");
            return -1L;
        }

        GoodsEntity goodsEntity = goodMapper.goodToGoodEntity(goods);
        return goodsDao.insert(goodsEntity);
    }

    @Override
    public List<Goods> view(int page, int recordsPerPage) {
        List<GoodsEntity> goods = goodsDao.findAll(page, recordsPerPage);
        return goods.stream()
                .map(goodMapper::goodEntityToGood)
                .collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return goodsDao.count();
    }

    @Override
    public void changeGoods(Integer changecode, Double changequant, Double changeprice) {
        if (Objects.isNull(changequant) || Objects.isNull(changeprice) || changequant < 0 || changeprice < 0 ||
                changequant > 100000 || changeprice > 100000 || changecode < 0) {
            LOGGER.warn("Good data for update is uncorrected");
            throw new InvalidDataRuntimeException("Good data for update is uncorrected");
        }

        Optional<GoodsEntity> goods = goodsDao.findGoods(changecode);
        if (goods.isPresent()) {
            goods.get().setQuant(changequant);
            goods.get().setPrice(changeprice);
            goodsDao.update(goods.get());
        }
    }
}
