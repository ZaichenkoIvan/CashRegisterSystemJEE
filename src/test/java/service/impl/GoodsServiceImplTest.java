package service.impl;

import dao.GoodsDao;
import domain.Goods;
import entity.GoodsEntity;
import exception.InvalidDataRuntimeException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.mapper.GoodMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoodsServiceImplTest {
    private static final Goods GOOD = getGood();

    private static final GoodsEntity ENTITY = getGoodEntity();

    private static final List<GoodsEntity> GOOD_ENTITIES = Collections.singletonList(ENTITY);

    private static final List<Goods> GOODS = Collections.singletonList(GOOD);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private GoodsDao repository;

    @Mock
    private GoodMapper mapper;

    @InjectMocks
    private GoodsServiceImpl service;

    @After
    public void resetMock() {
        reset(repository, mapper);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectCodeOfGoodInAddGood() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addGoods(-100, "Name", 100.0, 100.0, "measure", "comments");
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectQuantOfGoodInAddGood() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addGoods(-100, "Name", -100.0, 100.0, "measure", "comments");
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectPriceOfGoodInAddGood() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addGoods(100, "Name", 100.0, -100.0, "measure", "comments");
    }

    @Test
    public void shouldSaveGood() {
        when(repository.findGoods(anyInt())).thenReturn(Optional.empty());
        when(mapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);
        when(repository.insert(any(GoodsEntity.class))).thenReturn(1L);

        Long idGood = service.addGoods(100, "Name", 100.0, 100.0,
                "measure", "comments");

        assertThat(idGood, is(GOOD.getId()));
    }

    @Test
    public void shouldReturnThatGoodIsExist() {
        when(repository.findGoods(anyInt())).thenReturn(Optional.of(ENTITY));
        when(mapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);
        when(repository.insert(any(GoodsEntity.class))).thenReturn(1L);

        Long idGood = service.addGoods(100, "Name", 100.0, 100.0,
                "measure", "comments");

        assertThat(idGood, is(-1L));
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectCodeOfGoodInChangeGood() {
        exception.expect(InvalidDataRuntimeException.class);

        service.changeGoods(-100, 100.0, 100.0);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectQuantOfGoodInChangeGood() {
        exception.expect(InvalidDataRuntimeException.class);

        service.changeGoods(100, -100.0, 100.0);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectPriceOfGoodInChangeGood() {
        exception.expect(InvalidDataRuntimeException.class);

        service.changeGoods(100, 100.0, -100.0);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullQuantOfGoodInChangeGood() {
        exception.expect(InvalidDataRuntimeException.class);

        service.changeGoods(-100, null, 100.0);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullPriceOfGoodInChangeGood() {
        exception.expect(InvalidDataRuntimeException.class);

        service.changeGoods(-100, 100.0, null);
    }

    @Test
    public void shouldChangeGood() {
        when(repository.findGoods(anyInt())).thenReturn(Optional.of(ENTITY));

        service.changeGoods(100, 100.0, 100.0);

        verify(repository).update(any(GoodsEntity.class));
    }

    @Test
    public void shouldReturnCountGood() {
        when(repository.count()).thenReturn(5L);

        Long countGoods = service.count();

        assertThat(countGoods, is(5L));
        verify(repository).count();
    }

    @Test
    public void shouldReturnPagenationGood() {
        when(repository.findAll(anyInt(), anyInt())).thenReturn(GOOD_ENTITIES);
        when(mapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);

        List<Goods> pageGood = service.view(1, 1);

        assertThat(pageGood, is(GOODS));
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
}