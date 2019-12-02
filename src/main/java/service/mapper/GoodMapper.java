package service.mapper;

import domain.Goods;
import entity.GoodsEntity;

import java.util.Objects;

public class GoodMapper {
    public Goods goodEntityToGood(GoodsEntity goodsEntity) {
        if (Objects.isNull(goodsEntity)) {
            return null;
        }

        Goods goods = new Goods();
        goods.setId(goodsEntity.getId());
        goods.setCode(goodsEntity.getCode());
        goods.setName(goodsEntity.getName());
        goods.setQuant(goodsEntity.getQuant());
        goods.setPrice(goodsEntity.getPrice());
        goods.setMeasure(goodsEntity.getMeasure());
        goods.setComments(goodsEntity.getComments());

        return goods;
    }

    public GoodsEntity goodToGoodEntity(Goods goods) {
        if (Objects.isNull(goods)) {
            return null;
        }

        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setId(goods.getId());
        goodsEntity.setName(goods.getName());
        goodsEntity.setCode(goods.getCode());
        goodsEntity.setQuant(goods.getQuant());
        goodsEntity.setMeasure(goods.getMeasure());
        goodsEntity.setPrice(goods.getPrice());
        goodsEntity.setComments(goods.getComments());

        return goodsEntity;
    }
}
