package command.impl;

import domain.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationCommandTest {
    private static final User CASHIER = getCashier();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserService service;

    @Mock
    private HttpSession session;

    @InjectMocks
    private RegistrationCommand command;

    @After
    public void resetMock() {
        reset(request, response, service, session);
    }

    @Test
    public void executeShouldRegistr() {
        when(request.getParameter(anyString())).thenReturn("name").thenReturn("email").thenReturn("password");
        when(service.registration(anyString(), anyString(), anyString())).thenReturn(CASHIER);
        when(request.getSession()).thenReturn(session);

        String expected = "check";
        String actual = command.execute(request, response);

        assertThat(expected, is(actual));
    }

    @Test
    public void executeShouldReturnNull() {
        when(request.getParameter(anyString())).thenReturn("name").thenReturn("email").thenReturn("password");
        when(service.registration(anyString(), anyString(), anyString())).thenReturn(null);

        String actual = command.execute(request, response);

        assertThat(null, is(actual));
    }

    private static User getCashier() {
        User user = new User();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("password");
        user.setName("name");
        user.setIdUserType(1L);
        return user;
    }
}
