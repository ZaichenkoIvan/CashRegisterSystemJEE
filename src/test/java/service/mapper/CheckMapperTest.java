package service.mapper;

import domain.Check;
import entity.CheckEntity;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CheckMapperTest {
    private static final CheckEntity CHECK_ENTITY = getCheckEntity();

    private static final Check CHECK_DOMAIN = getCheck();

    private final CheckMapper checkMapper = new CheckMapper();

    @Test
    public void shouldMapCheckEntityToCheck() {
        Check actual = checkMapper.checkEntityToCheck(CHECK_ENTITY);

        assertThat(actual.getId(), is(CHECK_DOMAIN.getId()));
        assertThat(actual.getTotal(), is(CHECK_DOMAIN.getTotal()));
        assertThat(actual.getCanceled(), is(CHECK_DOMAIN.getCanceled()));
        assertThat(actual.getCrtime(), is(CHECK_DOMAIN.getCrtime()));
        assertThat(actual.getDiscount(), is(CHECK_DOMAIN.getDiscount()));
        assertThat(actual.getCreator(), is(CHECK_DOMAIN.getCreator()));
        assertThat(actual.getRegistration(), is(CHECK_DOMAIN.getRegistration()));
    }

    @Test
    public void shouldMapCheckToCheckEntity() {
        CheckEntity actual = checkMapper.checkToCheckEntity(CHECK_DOMAIN);

        assertThat(actual.getId(), is(CHECK_ENTITY.getId()));
        assertThat(actual.getTotal(), is(CHECK_ENTITY.getTotal()));
        assertThat(actual.getCanceled(), is(CHECK_ENTITY.getCanceled()));
        assertThat(actual.getCrtime(), is(CHECK_ENTITY.getCrtime()));
        assertThat(actual.getDiscount(), is(CHECK_ENTITY.getDiscount()));
        assertThat(actual.getCreator(), is(CHECK_ENTITY.getCreator()));
        assertThat(actual.getRegistration(), is(CHECK_ENTITY.getRegistration()));
    }

    @Test
    public void mapCheckToCheckEntityShouldReturnNull() {
        CheckEntity actual = checkMapper.checkToCheckEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapCheckEntityToCheckShouldReturnNull() {
        Check actual = checkMapper.checkEntityToCheck(null);

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
}
