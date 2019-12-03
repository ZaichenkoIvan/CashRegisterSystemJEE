package service.validator;

import domain.User;
import exception.InvalidDataRuntimeException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserValidatorTest {
    private static UserValidator validator = new UserValidator();

    @Test
    public void shouldCorrectValidateDriver() {
        User user = new User();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("password");
        user.setName("Name");
        user.setIdUserType(1L);

        validator.validate(user);

        assertThat("email@gmail.com", is(user.getLogin()));
        assertThat("password", is(user.getPassword()));
        assertThat("Name", is(user.getName()));
        assertThat(1L, is(user.getId()));
        assertThat(1L, is(user.getIdUserType()));
    }

    @Test(expected = InvalidDataRuntimeException.class)
    public void shouldThrowInvalidDataRuntimeExceptionValidatingInvalidEmail() {
        User user = new User();
        user.setId(1L);
        user.setLogin("Wrong Email");
        user.setPassword("password");
        user.setName("Name");
        user.setIdUserType(1L);

        validator.validate(user);
    }

    @Test(expected = InvalidDataRuntimeException.class)
    public void shouldThrowInvalidDataRuntimeExceptionValidatingNull() {
        validator.validate(null);
    }

    @Test(expected = InvalidDataRuntimeException.class)
    public void shouldThrowInvalidDataRuntimeExceptionValidatingInvalidName() {
        User user = new User();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("password");
        user.setName("Name1111");
        user.setIdUserType(1L);

        validator.validate(user);
    }

    @Test(expected = InvalidDataRuntimeException.class)
    public void shouldThrowInvalidDataRuntimeExceptionValidatingInvalidPassword() {
        User user = new User();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("Fail");
        user.setName("Name");
        user.setIdUserType(1L);

        validator.validate(user);
    }
}
