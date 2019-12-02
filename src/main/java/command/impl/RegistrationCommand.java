package main.java.command.impl;

import javax.servlet.http.*;

import main.java.command.Command;
import main.java.service.UserService;
import org.apache.log4j.Logger;

import main.java.domain.User;

public class RegistrationCommand implements Command {
	private final UserService userService;

	public RegistrationCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		User user = userService.registration(req.getParameter("name"), req.getParameter("email"), req.getParameter("password"));
		if(user != null) {
			req.getSession().setAttribute("user", user);
			return "check";
		} else {
			req.setAttribute("existsLogin",  req.getParameter("email"));
			return null;
		}
	}
}
