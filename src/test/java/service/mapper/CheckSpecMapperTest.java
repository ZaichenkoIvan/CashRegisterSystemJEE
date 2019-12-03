package service.mapper;

import domain.Check;
import domain.Checkspec;
import entity.CheckEntity;
import entity.CheckspecEntity;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckSpecMapperTest {
    private static final CheckEntity CHECK_ENTITY = getCheckEntity();

    private static final Check CHECK_DOMAIN = getCheck();

    private static final CheckspecEntity CHECKSPEC_ENTITY = getCheckspecEntity();

    private static final Checkspec CHECKSPEC_DOMAIN = getCheckspec();

    @Mock
    private CheckMapper checkMapper;

    @InjectMocks
    private CheckSpecMapper checkSpecMapper;

    @After
    public void resetMock() {
        reset(checkMapper);
    }

    @Test
    public void shouldMapOrderEntityToOrder() {
        when(checkMapper.checkEntityToCheck(any(CheckEntity.class))).thenReturn(CHECK_DOMAIN);

        Checkspec actual = checkSpecMapper.checkspecEntityToCheckspec(CHECKSPEC_ENTITY);

        assertThat(actual.getId(), is(CHECKSPEC_DOMAIN.getId()));
        assertThat(actual.getQuant(), is(CHECKSPEC_DOMAIN.getQuant()));
        assertThat(actual.getPrice(), is(CHECKSPEC_DOMAIN.getPrice()));
        assertThat(actual.getTotal(), is(CHECKSPEC_DOMAIN.getTotal()));
        assertThat(actual.getNdstotal(), is(CHECKSPEC_DOMAIN.getNdstotal()));
        assertThat(actual.getNds(), is(CHECKSPEC_DOMAIN.getNds()));
        assertThat(actual.getCanceled(), is(CHECKSPEC_DOMAIN.getCanceled()));
        assertThat(actual.getCheck(), is(CHECKSPEC_DOMAIN.getCheck()));
        assertThat(actual.getIdGood(), is(CHECKSPEC_DOMAIN.getIdGood()));
    }

    @Test
    public void shouldMapOrderToOrderEntity() {
        when(checkMapper.checkToCheckEntity(any(Check.class))).thenReturn(CHECK_ENTITY);

        CheckspecEntity actual = checkSpecMapper.checkspecToCheckspecEntity(CHECKSPEC_DOMAIN);

        assertThat(actual.getId(), is(CHECKSPEC_ENTITY.getId()));
        assertThat(actual.getQuant(), is(CHECKSPEC_ENTITY.getQuant()));
        assertThat(actual.getPrice(), is(CHECKSPEC_ENTITY.getPrice()));
        assertThat(actual.getTotal(), is(CHECKSPEC_ENTITY.getTotal()));
        assertThat(actual.getNdstotal(), is(CHECKSPEC_ENTITY.getNdstotal()));
        assertThat(actual.getNds(), is(CHECKSPEC_ENTITY.getNds()));
        assertThat(actual.getCanceled(), is(CHECKSPEC_ENTITY.getCanceled()));
        assertThat(actual.getCheck(), is(CHECKSPEC_ENTITY.getCheck()));
        assertThat(actual.getIdGood(), is(CHECKSPEC_ENTITY.getIdGood()));
    }

    @Test
    public void mapGoodToGoodEntityShouldReturnNull() {
        CheckspecEntity actual = checkSpecMapper.checkspecToCheckspecEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapGoodEntityToGoodShouldReturnNull() {
        Checkspec actual = checkSpecMapper.checkspecEntityToCheckspec(null);

        assertThat(actual, is(nullValue()));
    }

    private static CheckEntity getCheckEntity() {
        CheckEntity check = new CheckEntity();
        check.setTotal(100.0);
        check.setCanceled(1);
        check.setCreator(1L);
        check.setId(1L);
        check.setRegistration(1);
        check.setDiscount(1);
        return check;
    }

    private static Check getCheck() {
        Check check = new Check();
        check.setTotal(100.0);
        check.setCanceled(1);
        check.setCreator(1L);
        check.setId(1L);
        check.setRegistration(1);
        check.setDiscount(1);
        return check;
    }

    private static CheckspecEntity getCheckspecEntity() {
        CheckspecEntity checkspec = new CheckspecEntity();
        checkspec.setId(1L);
        checkspec.setQuant(100.0);
        checkspec.setPrice(100.0);
        checkspec.setTotal(100.0);
        checkspec.setNdstotal(100.0);
        checkspec.setNds(100);
        checkspec.setCanceled(1);
        checkspec.setCheck(CHECK_ENTITY);
        checkspec.setIdGood(1L);
        return checkspec;
    }

    private static Checkspec getCheckspec() {
        Checkspec checkspec = new Checkspec();
        checkspec.setId(1L);
        checkspec.setQuant(100.0);
        checkspec.setPrice(100.0);
        checkspec.setTotal(100.0);
        checkspec.setNdstotal(100.0);
        checkspec.setNds(100);
        checkspec.setCanceled(1);
        checkspec.setCheck(CHECK_DOMAIN);
        checkspec.setIdGood(1L);
        return checkspec;
    }
}
