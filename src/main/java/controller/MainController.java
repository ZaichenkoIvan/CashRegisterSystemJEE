package main.java.controller;

import main.java.command.Command;
import main.java.context.ApplicationContextInjector;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (command != null) {
            path = command.execute(req, resp);
            if (path != null) {
                resp.sendRedirect(path);
            }
        }
        if (command == null || path == null) {
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

