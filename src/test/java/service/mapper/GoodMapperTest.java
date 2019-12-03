package service.mapper;

import domain.Goods;
import entity.GoodsEntity;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GoodMapperTest {
    private static final GoodsEntity GOODS_ENTITY = getGoodEntity();

    private static final Goods GOODS_DOMAIN = getGood();

    private final GoodMapper goodMapper = new GoodMapper();

    @Test
    public void shouldMapGoodEntityToGood() {
        GoodsEntity actual = goodMapper.goodToGoodEntity(GOODS_DOMAIN);

        assertThat(actual.getId(), is(GOODS_ENTITY.getId()));
        assertThat(actual.getCode(), is(GOODS_ENTITY.getCode()));
        assertThat(actual.getName(), is(GOODS_ENTITY.getName()));
        assertThat(actual.getPrice(), is(GOODS_ENTITY.getPrice()));
        assertThat(actual.getQuant(), is(GOODS_ENTITY.getQuant()));
        assertThat(actual.getComments(), is(GOODS_ENTITY.getComments()));
        assertThat(actual.getMeasure(), is(GOODS_ENTITY.getMeasure()));
    }

    @Test
    public void shouldMapGoodToGoodEntity() {
        Goods actual = goodMapper.goodEntityToGood(GOODS_ENTITY);

        assertThat(actual.getId(), is(GOODS_DOMAIN.getId()));
        assertThat(actual.getCode(), is(GOODS_DOMAIN.getCode()));
        assertThat(actual.getName(), is(GOODS_DOMAIN.getName()));
        assertThat(actual.getPrice(), is(GOODS_DOMAIN.getPrice()));
        assertThat(actual.getQuant(), is(GOODS_DOMAIN.getQuant()));
        assertThat(actual.getComments(), is(GOODS_DOMAIN.getComments()));
        assertThat(actual.getMeasure(), is(GOODS_DOMAIN.getMeasure()));
    }

    @Test
    public void mapGoodToGoodEntityShouldReturnNull() {
        GoodsEntity actual = goodMapper.goodToGoodEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapGoodEntityToGoodShouldReturnNull() {
        Goods actual = goodMapper.goodEntityToGood(null);

        assertThat(actual, is(nullValue()));
    }

    private static GoodsEntity getGoodEntity() {
        GoodsEntity goods = new GoodsEntity();
        goods.setId(1L);
        goods.setCode(1);
        goods.setName("Name");
        goods.setQuant(100);
        goods.setPrice(100);
        goods.setMeasure("measure");
        goods.setComments("comments");
        return goods;
    }

    private static Goods getGood() {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setCode(1);
        goods.setName("Name");
        goods.setQuant(100);
        goods.setPrice(100);
        goods.setMeasure("measure");
        goods.setComments("comments");
        return goods;
    }
}
