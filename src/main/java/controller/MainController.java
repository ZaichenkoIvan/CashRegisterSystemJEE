package controller;

import command.Command;
import context.ApplicationContextInjector;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class MainController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
		String logfilename = getInitParameter("logfile");
		String pref = getServletContext().getRealPath("/");
		PropertyConfigurator.configure(pref+logfilename);
        ApplicationContextInjector.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = ApplicationContextInjector.getCommand(req);
        String path = null;
        if (Objects.nonNull(command)) {
            path = command.execute(req, resp);
            if (Objects.nonNull(path)) {
                resp.sendRedirect(path);
            }
        }

        if (Objects.isNull(command) || Objects.isNull(path)) {
            String page = getPage(req);
            req.getRequestDispatcher("/WEB-INF/view" + page + ".jsp").forward(req, resp);
        }
    }

    private String getPage(HttpServletRequest req) {
        String page = req.getServletPath();
        if (page.equals("/") || page.equals("/login") || page.equals("/logout") ) {
            page = "/index";
        }
        return page;
    }
}

