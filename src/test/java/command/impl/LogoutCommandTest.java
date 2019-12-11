package command.impl;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogoutCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LogoutCommand command;

    @After
    public void resetMock() {
        reset(request, response, session);
    }

    @Test
    public void executeShouldLogout() {
        when(request.getSession()).thenReturn(session);
        when(request.getAttribute(anyString())).thenReturn("ua").thenReturn("password");

        String expected = "login";
        String actual = command.execute(request, response);

        assertThat(expected, is(actual));
        verify(session).invalidate();
    }
}
