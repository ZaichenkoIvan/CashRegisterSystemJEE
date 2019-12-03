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
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.mapper.CheckMapper;
import service.mapper.CheckSpecMapper;
import service.mapper.GoodMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckServiceImplTest {

    private static final Goods GOOD = getGood();

    private static final GoodsEntity GOODS_ENTITY = getGoodEntity();

    private static final Check CHECK = getCheck();

    private static final CheckEntity CHECK_ENTITY = getCheckEntity();

    private static final CheckspecEntity CHECKSPEC_ENTITY = getCheckspecEntity();

    private static final Checkspec CHECKSPEC = getCheckspec();

    private static final List<Checkspec> CHECKSPECS = Collections.singletonList(CHECKSPEC);

    private static final List<CheckspecEntity> CHECKSPEC_ENTITIES = Collections.singletonList(CHECKSPEC_ENTITY);

    private static final User USER_DOMAIN = getUser();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private CheckDao checkDao;

    @Mock
    private CheckSpecDao checkSpecDao;

    @Mock
    private GoodsDao goodsDao;

    @Mock
    private GoodMapper goodMapper;

    @Mock
    private CheckMapper checkMapper;

    @Mock
    private CheckSpecMapper checkSpecMapper;

    @InjectMocks
    private CheckServiceImpl service;

    @After
    public void resetMock() {
        reset(checkDao, checkSpecDao, goodsDao, checkMapper, checkSpecMapper, goodMapper);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectQuantInAddCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addCheckSpec(100, -100.0, "100");
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectNdsInAddCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addCheckSpec(100, 100.0, "-100");
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullNdsInAddCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addCheckSpec(100, 100.0, null);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullCodeInAddCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addCheckSpec(null, 100.0, "100");
    }

    @Test
    public void shouldDontFindGoodInThisCheckspec() {
        when(goodsDao.findGoods(anyInt())).thenReturn(Optional.empty());
        when(goodMapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(null);

        Checkspec checkspec = service.addCheckSpec(100, 100.0, "100");

        assertThat(checkspec, nullValue());
    }

    @Test
    public void shouldThrowNotEnoughGoodsQuantRuntimeExceptionWithNullCodeInAddCheckspec() {
        exception.expect(NotEnoughGoodsQuantRuntimeException.class);
        when(goodsDao.findGoods(anyInt())).thenReturn(Optional.ofNullable(GOODS_ENTITY));
        when(goodMapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);

        service.addCheckSpec(100, 1000.0, "100");
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullUserInAddCheck() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addCheck(null, CHECKSPECS);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithEmptyListInAddCheck() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addCheck(USER_DOMAIN, new ArrayList<>());
    }

    @Test
    public void shouldAddCheck() {
        when(checkMapper.checkToCheckEntity(any(Check.class))).thenReturn(CHECK_ENTITY);
        when(checkDao.insert(any(CheckEntity.class))).thenReturn(1L);
        when(goodsDao.findById(anyLong())).thenReturn(Optional.ofNullable(GOODS_ENTITY));

        service.addCheck(USER_DOMAIN, CHECKSPECS);

        verify(checkMapper).checkToCheckEntity(any(Check.class));
        verify(checkDao).insert(any(CheckEntity.class));
        verify(goodsDao).findById(anyLong());
        verify(goodsDao).update(any(GoodsEntity.class));
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullCheckInFindAllCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        service.findAllCheckspecByCheckId(null);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithInvalidCheckInFindAllCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        service.findAllCheckspecByCheckId(-1L);
    }

    @Test
    public void shouldFindAllCheckspecByCheckId() {
        when(checkSpecDao.findAllByCheckId(anyLong())).thenReturn(CHECKSPEC_ENTITIES);
        when(checkSpecMapper.checkspecEntityToCheckspec(any(CheckspecEntity.class))).thenReturn(CHECKSPEC);

        List<Checkspec> checkspecs = service.findAllCheckspecByCheckId(CHECK.getId());

        assertThat(checkspecs, is(CHECKSPECS));
    }


    @Test
    public void shouldReturnCheck() {
        when(checkDao.findById(anyLong())).thenReturn(Optional.of(CHECK_ENTITY));
        when(checkMapper.checkEntityToCheck(any(CheckEntity.class))).thenReturn(CHECK);

        Check check = service.findById(1L);

        verify(checkDao).findById(anyLong());
        verify(checkMapper).checkEntityToCheck(any(CheckEntity.class));

        assertThat(check, is(CHECK));
    }

    @Test
    public void shouldDontFindCheck() {
        exception.expect(InvalidDataRuntimeException.class);
        when(checkDao.findById(anyLong())).thenReturn(Optional.empty());
        when(checkMapper.checkEntityToCheck(any(CheckEntity.class))).thenReturn(null);

        service.findById(1L);

        verify(checkDao).findById(anyLong());
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNegativeId() {
        exception.expect(InvalidDataRuntimeException.class);

        service.findById(-1L);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullId() {
        exception.expect(InvalidDataRuntimeException.class);

        service.findById(null);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullCancelCheck() {
        exception.expect(InvalidDataRuntimeException.class);

        service.cancelCheck(null);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithNullCheckForCancelCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        service.cancelCheckSpec(CHECKSPECS, 1, null);
    }

    @Test
    public void shouldSaveCheckspec() {
        when(goodsDao.findGoods(anyInt())).thenReturn(Optional.ofNullable(GOODS_ENTITY));
        when(goodMapper.goodEntityToGood(any(GoodsEntity.class))).thenReturn(GOOD);

        Checkspec actual = service.addCheckSpec(100, 100.0, "100");

        Checkspec checkspec = new Checkspec();
        checkspec.setQuant(100.0);
        checkspec.setPrice(100.0);
        checkspec.setTotal(10000.0);
        checkspec.setNdstotal(10000.0);
        checkspec.setNds(100);
        checkspec.setCanceled(0);
        checkspec.setIdGood(1L);
        checkspec.setXcode(1);
        checkspec.setXname("Name");

        assertThat(actual, is(checkspec));
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithInvalidCheckpecNumberForCancelCheckspec() {
        exception.expect(InvalidDataRuntimeException.class);

        service.cancelCheckSpec(CHECKSPECS, -1, CHECK);
    }


    @Test
    public void shouldCancelCheck() {
        when(checkMapper.checkToCheckEntity(any(Check.class))).thenReturn(CHECK_ENTITY);

        service.cancelCheck(CHECK);

        verify(checkDao).update(any(CheckEntity.class));
    }

    private static CheckEntity getCheckEntity() {
        CheckEntity check = new CheckEntity();
        check.setTotal(10000.0);
        check.setCanceled(1);
        check.setCreator(1L);
        check.setId(1L);
        check.setRegistration(1);
        check.setDiscount(1);
        return check;
    }

    @Test
    public void shouldCancelCheckspec() {
        when(checkSpecMapper.checkspecToCheckspecEntity(any(Checkspec.class))).thenReturn(CHECKSPEC_ENTITY);
        when(checkMapper.checkToCheckEntity(any(Check.class))).thenReturn(CHECK_ENTITY);
        when(checkDao.findById(anyLong())).thenReturn(Optional.empty());

        service.cancelCheckSpec(CHECKSPECS, 1, CHECK);

        verify(checkSpecDao).update(any(CheckspecEntity.class));
        verify(checkDao).update(any(CheckEntity.class));
    }

    private static Check getCheck() {
        Check check = new Check();
        check.setTotal(10000.0);
        check.setCanceled(1);
        check.setCreator(1L);
        check.setId(1L);
        check.setRegistration(1);
        check.setDiscount(1);
        return check;
    }

    private static CheckspecEntity getCheckspecEntity() {
        CheckspecEntity checkspec = new CheckspecEntity();
        checkspec.setQuant(100.0);
        checkspec.setPrice(100.0);
        checkspec.setTotal(10000.0);
        checkspec.setNdstotal(10000.0);
        checkspec.setNds(100);
        checkspec.setCanceled(0);
        checkspec.setIdGood(1L);
        checkspec.setXcode(1);
        checkspec.setXname("Name");
        return checkspec;
    }

    private static Checkspec getCheckspec() {
        Checkspec checkspec = new Checkspec();
        checkspec.setQuant(100.0);
        checkspec.setPrice(100.0);
        checkspec.setTotal(10000.0);
        checkspec.setNdstotal(10000.0);
        checkspec.setNds(100);
        checkspec.setCanceled(0);
        checkspec.setIdGood(1L);
        checkspec.setXcode(1);
        checkspec.setXname("Name");
        return checkspec;
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

    private static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("password");
        user.setName("name");
        user.setIdUserType(1L);
        return user;
    }
}
