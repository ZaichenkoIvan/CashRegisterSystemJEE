package main.java.controller;

import javax.servlet.http.HttpServletRequest;

import main.java.command.Command;
import main.java.command.CommandFactory;
import main.java.command.CommandFactory.Commands;

public class ControllerHelper {

	public static Command getCommand(HttpServletRequest req) {
		
		CommandFactory commands = CommandFactory.getInstance();
		switch(req.getServletPath()) {
		    case "/":
		    case "/login":
		    	if (req.getParameter("btnLogin") != null && req.getParameter("email") != null) {
		    		return commands.getCommand(Commands.LOGIN);
		    	}
		    	break;
		    case "/logout":
		    	return commands.getCommand(Commands.LOGOUT);
		    case "/registration":
		    	if (req.getParameter("btnReg") != null) {
		    		return commands.getCommand(Commands.REGISTRATION);
		    	}
		    	break;
		    case "/goods":
		    	return commands.getCommand(Commands.GOODS);
		    case "/check":
				if (req.getParameter("btnAddCheckspec") != null) {
					return commands.getCommand(Commands.CHECKSPEC);
				} else if (req.getParameter("btnCreateCheck") != null) {
					return commands.getCommand(Commands.CHECK);
				}
				break;
		    case "/cancel":
		    	return commands.getCommand(Commands.CANCEL);
			default:
				return null;
		}
		return null;
	}
	
	/**
	 * Получить страницу для отображения
	 * @param req HttpServletRequest
	 * @return page страница
	 */
	public static String getPage(HttpServletRequest req) {
		String page = req.getServletPath();
		if (page.equals("/") || page.equals("/login") || page.equals("/logout") ) {
			page = "/index";
		}
		return page;
	}
}
