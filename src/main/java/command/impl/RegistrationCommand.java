package main.java.command.impl;

import javax.servlet.http.*;

import main.java.command.Command;
import main.java.service.UserService;
import main.java.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import main.java.entity.User;

public class RegistrationCommand implements Command {
	private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

	private final UserService userService;

	public RegistrationCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		User user = userService.registration(req.getParameter("name"), req.getParameter("email"), req.getParameter("password"));
		if(user != null) {
			req.getSession().setAttribute("user", user);
			LOGGER.info("Регистрация нового пользователя " + req.getParameter("name"));
			return "check";
		} else {
			LOGGER.info("Регистрация не удалась. Пользователь с таким email уже существует");
			req.setAttribute("existsLogin",  req.getParameter("email"));
			return null;
		}		
	}
}
