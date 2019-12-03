package service.mapper;

import domain.User;
import domain.UserType;
import entity.UserEntity;
import entity.UserTypeEntity;
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
public class UserMapperTest {
    private static final UserTypeEntity USER_TYPE_ENTITY = getUserTypeEntity();

    private static final UserType USER_TYPE_DOMAIN = getUserType();

    private static final UserEntity USER_ENTITY = getUserEntity();

    private static final User USER_DOMAIN = getUser();

    @Mock
    private UserTypeMapper userTypeMapper;

    @InjectMocks
    private UserMapper userMapper;

    @After
    public void resetMock() {
        reset(userTypeMapper);
    }

    @Test
    public void shouldMapUserEntityToUser() {
        when(userTypeMapper.userTypeEntityToUserType(any(UserTypeEntity.class))).thenReturn(USER_TYPE_DOMAIN);

        User actual = userMapper.userEntityToUser(USER_ENTITY);

        assertThat(actual.getId(), is(USER_DOMAIN.getId()));
        assertThat(actual.getName(), is(USER_DOMAIN.getName()));
        assertThat(actual.getLogin(), is(USER_DOMAIN.getLogin()));
        assertThat(actual.getPassword(), is(USER_DOMAIN.getPassword()));
        assertThat(actual.getIdUserType(), is(USER_DOMAIN.getIdUserType()));
        assertThat(actual.getUserType(), is(USER_DOMAIN.getUserType()));
    }

    @Test
    public void shouldMapUserToUserEntity() {
        when(userTypeMapper.userTypeToUserTypeEntity(any(UserType.class))).thenReturn(USER_TYPE_ENTITY);

        UserEntity actual = userMapper.userToUserEntity(USER_DOMAIN);

        assertThat(actual.getId(), is(USER_ENTITY.getId()));
        assertThat(actual.getName(), is(USER_ENTITY.getName()));
        assertThat(actual.getLogin(), is(USER_ENTITY.getLogin()));
        assertThat(actual.getPassword(), is(USER_ENTITY.getPassword()));
        assertThat(actual.getIdUserType(), is(USER_ENTITY.getIdUserType()));
        assertThat(actual.getUserType(), is(USER_ENTITY.getUserType()));
    }

    @Test
    public void mapUserToUserEntityShouldReturnNull() {
        UserEntity actual = userMapper.userToUserEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapUserEntityToUserShouldReturnNull() {
        User actual = userMapper.userEntityToUser(null);

        assertThat(actual, is(nullValue()));
    }

    private static UserEntity getUserEntity() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("password");
        user.setName("name");
        user.setUserType(USER_TYPE_ENTITY);
        user.setIdUserType(1L);
        return user;
    }

    private static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("password");
        user.setName("name");
        user.setUserType(USER_TYPE_DOMAIN);
        user.setIdUserType(1L);
        return user;
    }

    private static UserTypeEntity getUserTypeEntity() {
        UserTypeEntity userType = new UserTypeEntity();
        userType.setId(1L);
        userType.setType("cashier");
        userType.setDescription("Cashier");
        return userType;
    }

    private static UserType getUserType() {
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setType("cashier");
        userType.setDescription("Cashier");
        return userType;
    }
}
