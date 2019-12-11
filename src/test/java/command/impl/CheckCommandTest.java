package command.impl;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.CheckService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CheckService service;

    @Mock
    private HttpSession session;

    @InjectMocks
    private CheckCommand command;

    @After
    public void resetMock() {
        reset(request, response, session, service);
    }

    @Test
    public void executeShouldAddCheck() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("addcheckspecs")).thenReturn("1");
        when(request.getParameter("btnCreateCheck")).thenReturn(null);

        String actual = command.execute(request, response);

        assertThat(null, is(actual));
    }
}
