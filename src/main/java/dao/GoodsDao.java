package main.java.dao;

import main.java.entity.Goods;

import java.util.List;

public interface GoodsDao extends CrudDao<Goods> {
    Goods findGoods(int code);

    Goods findGoods(String name);

    List<Goods> findAll(int page, int recordsPerPage);

    long count();

    void update(Goods goods);
}
