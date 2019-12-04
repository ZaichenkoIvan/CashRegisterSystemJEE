package dao;

import entity.GoodsEntity;

import java.util.List;
import java.util.Optional;

public interface GoodsDao extends CrudDao<GoodsEntity> {
    Optional<GoodsEntity> findGoods(int code);

    Optional<GoodsEntity> findGoods(String name);

    List<GoodsEntity> findAll(int page, int recordsPerPage);

    long count();

    void update(GoodsEntity goods);
}
