package main.java.service.impl;

import main.java.dao.GoodsDao;
import main.java.entity.Goods;
import main.java.exception.InvalidDataRuntimeException;
import main.java.service.GoodsService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;

public class GoodsServiceImpl implements GoodsService {

    private static final Logger LOGGER = Logger.getLogger(GoodsServiceImpl.class);

    private final GoodsDao goodsDao;

    public GoodsServiceImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    @Override
    public Long addGoods(int code, String name, double quant, double price, String measure, String comments) {
        if (quant < 0 || price < 0 || quant > 100000 || price > 100000 || code < 0) {
            LOGGER.error("Good data is uncorrected");
            throw new InvalidDataRuntimeException("Good data is uncorrected");
        }

        Goods goods = new Goods();
        goods.setCode(code);
        goods.setName(name);
        goods.setQuant(quant);
        goods.setPrice(price);
        goods.setMeasure(measure);
        goods.setComments(comments);
        Goods existsGood = goodsDao.findGoods(code);
        if (existsGood != null) {
            LOGGER.info("Товар с кодом " + code + " уже существует");
            return -1L;
        } else {
            existsGood = goodsDao.findGoods(name);
            if (existsGood != null) {
                LOGGER.info("Товар " + name + " уже существует");
                return -2L;
            } else {
                LOGGER.info("Товар добавлен");
                return goodsDao.insert(goods);
            }
        }
    }

    @Override
    public List<Goods> view(int page, int recordsPerPage) {
        List<Goods> goods = goodsDao.findAll(page, recordsPerPage);
        return (goods.size() > 0 ? goods : null);
    }

    @Override
    public Long count() {
        return goodsDao.count();
    }

    @Override
    public void changeGoods(Integer changecode, Double changequant, Double changeprice) {
        if (Objects.isNull(changequant) || Objects.isNull(changeprice) || changequant < 0 || changeprice < 0 ||
                changequant > 100000 || changeprice > 100000 || changecode < 0) {
            LOGGER.error("Good data for update is uncorrected");
            throw new InvalidDataRuntimeException("Good data for update is uncorrected");
        }

        Goods goods = goodsDao.findGoods(changecode);
        if (Objects.nonNull(goods)) {
            goods.setQuant(changequant);
            goods.setPrice(changeprice);
            goodsDao.update(goods);
        }
    }
}
