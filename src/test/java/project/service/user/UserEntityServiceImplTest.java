package project.service.user;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import project.model.dao.UserDao;
import project.model.domain.User;
import project.model.entity.UserEntity;
import project.model.exception.AlreadyRegisteredException;
import project.model.exception.InvalidRegistrationException;
import project.model.exception.UserNotFoundException;
import project.model.service.encoder.PasswordEncoder;
import project.model.service.impl.UserServiceImpl;
import project.model.service.mapper.UserMapper;
import project.model.service.validator.UserValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserEntityServiceImplTest {
    private static final UserEntity USER_ENTITY = UserEntity.builder()
            .withId(1)
            .withName("Name")
            .withSurname("Surname")
            .withPassword("ENCODED")
            .withEmail("correct@gmail.com")
            .build();
    ;
    private static final User USER = User.builder()
            .withName("Name")
            .withSurname("Surname")
            .withPassword("Hello")
            .withEmail("correct@gmail.com")
            .build();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private UserDao repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private UserValidator validator;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @After
    public void resetMock() {
        reset(repository, validator, encoder);
    }

    @Test
    public void shouldRegisterUser() {
        when(repository.save(any(UserEntity.class))).thenReturn(true);
        when(mapper.mapUserToUserEntity(any(User.class))).thenReturn(USER_ENTITY);
        when(encoder.encode(any(String.class))).thenReturn(Optional.of(USER_ENTITY.getPassword()));
        boolean actual = userService.register(USER);

        assertTrue(actual);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenRegisterUser() {
        exception.expect(AlreadyRegisteredException.class);
        exception.expectMessage("User is already registered by this e-mail");

        when(repository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(USER_ENTITY));


        userService.register(USER);
    }

    @Test
    public void shouldThrowInvalidRegistrationExceptionWhenRegisterNullUser() {
        exception.expect(InvalidRegistrationException.class);

        doThrow(InvalidRegistrationException.class).when(validator).validate(null);
        userService.register(null);
    }

    @Test
    public void shouldLoginUser() {
        when(repository.findByEmail("correct@gmail.com")).thenReturn(Optional.of(USER_ENTITY));
        when(encoder.encode("hello")).thenReturn(Optional.of(USER_ENTITY.getPassword()));
        when(mapper.mapUserEntityToUser(any(UserEntity.class))).thenReturn(USER);

        User actual = userService.login("correct@gmail.com", "hello");

        assertEquals(USER, actual);
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWithIncorrectPassword() {
        exception.expect(UserNotFoundException.class);
        exception.expectMessage("Incorrect password");

        when(encoder.encode(any(String.class))).thenReturn(Optional.of("test"));
        when(repository.findByEmail("correct@gmail.com")).thenReturn(Optional.ofNullable(USER_ENTITY));

        userService.login("correct@gmail.com", "test");
    }

    @Test
    public void shouldReturnAllUsers() {
        List<User> expected = Collections.singletonList(USER);
        List<UserEntity> entities = Collections.singletonList(USER_ENTITY);

        when(repository.findAll(1, 2)).thenReturn(entities);
        when(mapper.mapUserEntityToUser(USER_ENTITY)).thenReturn(USER);
        List<User> actual = userService.findAll(1, 2);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyListWhenThereIsNoUsers() {
        List<User> expected = Collections.emptyList();

        when(repository.findAll(1, 2)).thenReturn(Collections.emptyList());
        List<User> actual = userService.findAll(1, 2);

        assertEquals(expected, actual);
    }
}
