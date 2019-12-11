package command.impl;

import domain.Checkspec;
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
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class CheckSpecCommandTest {
    private static final Checkspec CHECKSPEC = getCheckspec();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CheckService service;

    @Mock
    private HttpSession session;

    @InjectMocks
    private CheckSpecCommand command;

    @After
    public void resetMock() {
        reset(request, response, session, service);
    }

    @Test
    public void executeShouldSaveGood() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("addcheckspecs")).thenReturn(null);
        when(request.getParameter("xcode")).thenReturn("1");
        when(request.getParameter("xname")).thenReturn("name");
        when(request.getParameter("quant")).thenReturn("100");
        when(request.getParameter("nds")).thenReturn("20");
        when(service.addCheckSpec(anyString(), anyString(), anyDouble(), anyString())).thenReturn(CHECKSPEC);

        String actual = command.execute(request, response);

        assertThat(null, is(actual));
    }

    private static Checkspec getCheckspec() {
        Checkspec checkspec = new Checkspec();
        checkspec.setQuant(100.0);
        checkspec.setPrice(100.0);
        checkspec.setTotal(10000.0);
        checkspec.setNdstotal(10000.0);
        checkspec.setNds(100);
        checkspec.setCanceled(0);
        checkspec.setIdGood(1L);
        checkspec.setXcode(1);
        checkspec.setXname("Name");
        return checkspec;
    }
}