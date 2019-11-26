package main.java.controller;

import main.java.command.Command;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();		
//		String logfilename = getInitParameter("logfile");// logfilename - получает имя файла конфигурации из параметра инициализации сервлета
//		String pref = getServletContext().getRealPath("/");
//		PropertyConfigurator.configure(pref+logfilename);
//		Logger logger = Logger.getRootLogger();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String lang= req.getParameter("lang");
		if (lang != null) {
			req.getSession().setAttribute("language", lang);
		}
		processRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
		Command command = ControllerHelper.getCommand(req);
		String path = null;
		if (command != null ) {
			path = command.execute(req, resp);
			if (path != null) {
				resp.sendRedirect(path);
			}
		}
		if (command == null || path == null) {
			String page = ControllerHelper.getPage(req);
			req.getRequestDispatcher("/WEB-INF/view" + page + ".jsp").forward(req, resp);
		}
	}	
}

