package main.java.service;

import main.java.entity.Goods;

import java.util.List;

public interface GoodsService {
    Long addGoods(int code, String name, double quant, double price, String measure, String comments);

    List<Goods> view(int page, int recordsPerPage);

    Long count();

    void changeGoods(Integer changecode, Double changequant, Double changeprice);
}
