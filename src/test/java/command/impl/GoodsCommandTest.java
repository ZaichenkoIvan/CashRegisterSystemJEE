package command.impl;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.GoodsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoodsCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private GoodsService service;

    @Mock
    private HttpSession session;

    @InjectMocks
    private GoodsCommand command;

    @After
    public void resetMock() {
        reset(request, response, session, service);
    }

    @Test
    public void executeShouldSaveGood() {
        when(request.getParameter("btnSaveGood")).thenReturn("btnSaveGood");
        when(request.getParameter("code")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("quant")).thenReturn("100");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("measure")).thenReturn("measure");
        when(request.getParameter("comments")).thenReturn("comments");
        when(service.addGoods(anyInt(), anyString(), anyDouble(), anyDouble(), anyString(), anyString())).thenReturn(1L);
        when(service.view(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(service.count()).thenReturn(1L);

        String actual = command.execute(request, response);

        assertThat(null, is(actual));
        verify(request, times(4)).setAttribute(anyString(), any());
    }

    @Test
    public void executeShouldNotSaveGood() {
        when(request.getParameter("btnSaveGood")).thenReturn("btnSaveGood");
        when(request.getParameter("code")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("quant")).thenReturn("100");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("measure")).thenReturn("measure");
        when(request.getParameter("comments")).thenReturn("comments");
        when(service.addGoods(anyInt(), anyString(), anyDouble(), anyDouble(), anyString(), anyString())).thenReturn(-1L);
        when(service.view(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(service.count()).thenReturn(1L);

        String actual = command.execute(request, response);

        assertThat(null, is(actual));
        verify(request, times(5)).setAttribute(anyString(), any());
    }

    @Test
    public void executeShouldChangeGood() {
        when(request.getParameter("btnChangeGoods")).thenReturn("btnChangeGoods");
        when(request.getParameter("changequant")).thenReturn("100");
        when(request.getParameter("changeprice")).thenReturn("100");
        when(request.getParameter("changecode")).thenReturn("1");
        when(service.view(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(service.count()).thenReturn(1L);

        String actual = command.execute(request, response);

        assertThat(null, is(actual));

        verify(service).changeGoods(anyInt(), anyDouble(), anyDouble());

        verify(request, times(3)).setAttribute(anyString(), any());

    }
}