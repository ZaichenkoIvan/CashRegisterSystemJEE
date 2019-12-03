package service.impl;

import dao.UserDao;
import dao.UserTypeDao;
import domain.User;
import domain.UserType;
import entity.UserEntity;
import entity.UserTypeEntity;
import exception.InvalidDataRuntimeException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.encoder.EncoderPassword;
import service.mapper.UserMapper;
import service.validator.UserValidator;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final UserType USER_TYPE = getUserType();
    private static final UserTypeEntity USER_TYPE_ENTITY = getUserTypeEntity();
    private static final User USER = getUser();
    private static final UserEntity ENTITY = getUserEntity();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private UserDao repository;

    @Mock
    private UserTypeDao userTypeDao;

    @Mock
    private UserMapper mapper;

    @Mock
    private UserValidator userValidator;

    @Mock
    private EncoderPassword encoder;

    @InjectMocks
    private UserServiceImpl service;

    @After
    public void resetMock() {
        reset(repository, mapper, userTypeDao, userValidator, encoder);
    }

    @Test
    public void registrationClientShouldThrowUnCorrectInvalidDataRuntimeExceptionWithEmptyName() {
        exception.expect(InvalidDataRuntimeException.class);

        service.registration(null, "email@gmail.com", "password");
    }

    @Test
    public void registrationClientShouldThrowUnCorrectInvalidDataRuntimeExceptionWithEmptyLogin() {
        exception.expect(InvalidDataRuntimeException.class);

        service.registration("Username", null, "password");
    }

    @Test
    public void registrationClientShouldThrowUnCorrectInvalidDataRuntimeExceptionWithEmptyPasswodrd() {
        exception.expect(InvalidDataRuntimeException.class);

        service.registration("Username", "email@gmail.com", null);
    }

    @Test
    public void registrUserShouldSaveUser() {
        when(repository.findUserByLogin(any(String.class))).thenReturn(Optional.ofNullable(ENTITY));
        when(encoder.encode(anyString())).thenReturn(USER.getPassword());
        when(userTypeDao.findUserType(any(String.class))).thenReturn(1L);
        when(mapper.userToUserEntity(any(User.class))).thenReturn(ENTITY);
        when(repository.insert(any(UserEntity.class))).thenReturn(1L);

        User actual = new User();
        actual.setId(1L);
        actual.setLogin("email@gmail.com");
        actual.setPassword("password");
        actual.setName("name");
        actual.setUserType(USER_TYPE);
        actual.setIdUserType(1L);

        service.registration(actual.getName(), actual.getLogin(), actual.getPassword());
        USER.setPassword(actual.getPassword());

        assertThat(actual, is(USER));
        verify(userValidator).validate(any(User.class));

    }

    @Test
    public void loadUserByUsernameShouldThrowUnCorrectInputDataRuntimeExceptionWithEmptyLogin() {
        exception.expect(InvalidDataRuntimeException.class);

        service.findUser(null, "password");
    }

    @Test
    public void loadUserByUsernameShouldThrowUnCorrectInputDataRuntimeExceptionWithEmptyPassword() {
        exception.expect(InvalidDataRuntimeException.class);

        service.findUser("Login", null);
    }

    @Test
    public void findUserByUsernameShouldReturnRegisteredClient() {
        when(encoder.encode(anyString())).thenReturn(USER.getPassword());
        when(repository.findUserByLogin(anyString())).thenReturn(Optional.of(ENTITY));
        when(mapper.userEntityToUser(any(UserEntity.class))).thenReturn(USER);

        User actual = service.findUser("email@gmail.com", "password");

        assertThat(actual, is(USER));
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
        user.setUserType(USER_TYPE);
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