package command.impl;

import domain.User;
import domain.UserType;
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
public class LoginCommandTest {

    private static final UserType USER_TYPE_GOOD_SPEC = getUserTypeGoodSpec();

    private static final UserType USER_TYPE_CASHIER = getUserTypeCashier();

    private static final UserType USER_TYPE_SENIOR_CASHIER = getUserTypeSeniorCashier();

    private static final User CASHIER = getCashier();

    private static final User SENIOR_CASHIER = getSeniorCashier();

    private static final User GOOD_SPEC = getGoodSpec();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserService service;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LoginCommand command;

    @After
    public void resetMock() {
        reset(request, response, service, session);
    }

    @Test
    public void executeShouldLoginGoodSpec() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(anyString())).thenReturn("email").thenReturn("password");
        when(service.findUser(anyString(), anyString())).thenReturn(GOOD_SPEC);

        String expected = "goods";
        String actual = command.execute(request, response);

        assertThat(expected, is(actual));
    }

    @Test
    public void executeShouldLoginCashier() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(anyString())).thenReturn("email").thenReturn("password");
        when(service.findUser(anyString(), anyString())).thenReturn(CASHIER);

        String expected = "check";
        String actual = command.execute(request, response);

        assertThat(expected, is(actual));
    }

    @Test
    public void executeShouldLoginSeniorCashier() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(anyString())).thenReturn("email").thenReturn("password");
        when(service.findUser(anyString(), anyString())).thenReturn(SENIOR_CASHIER);

        String expected = "cancel";
        String actual = command.execute(request, response);

        assertThat(expected, is(actual));
    }

    private static User getCashier() {
        User user = new User();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("password");
        user.setName("name");
        user.setIdUserType(1L);
        user.setUserType(USER_TYPE_CASHIER);
        return user;
    }

    private static User getSeniorCashier() {
        User user = new User();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("password");
        user.setName("name");
        user.setIdUserType(1L);
        user.setUserType(USER_TYPE_SENIOR_CASHIER);
        return user;
    }

    private static User getGoodSpec() {
        User user = new User();
        user.setId(1L);
        user.setLogin("email@gmail.com");
        user.setPassword("password");
        user.setName("name");
        user.setIdUserType(1L);
        user.setUserType(USER_TYPE_GOOD_SPEC);
        return user;
    }

    private static UserType getUserTypeCashier() {
        UserType userType = new UserType();
        userType.setId(3L);
        userType.setType("cashier");
        userType.setDescription("Cashier");
        return userType;
    }

    private static UserType getUserTypeSeniorCashier() {
        UserType userType = new UserType();
        userType.setId(2L);
        userType.setType("senior_cashier");
        userType.setDescription("Senior Cashier");
        return userType;
    }

    private static UserType getUserTypeGoodSpec() {
        UserType userType = new UserType();
        userType.setId(4L);
        userType.setType("goods_spec");
        userType.setDescription("Good Spec");
        return userType;
    }
}
