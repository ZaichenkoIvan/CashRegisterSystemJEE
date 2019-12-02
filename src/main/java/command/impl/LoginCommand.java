package main.java.command.impl;

import main.java.command.Command;
import main.java.domain.User;
import main.java.domain.UserType;
import main.java.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = userService.findUser(req.getParameter("email"), req.getParameter("password"));
        if (user != null) {
            session.setAttribute("userNotExists", null);
            session.setAttribute("user", user);
            LOGGER.info("Авторизация пользователя " + user.getName());
            UserType type = user.getUserType();
            if (type.getType().equalsIgnoreCase("goods_spec")) {
                return "goods";
            } else if (type.getType().equalsIgnoreCase("cashier")) {
                return "check";
            } else if (type.getType().equalsIgnoreCase("senior_cashier")) {
                return "cancel";
            }
        } else {
            if (session != null) {
                session.setAttribute("userNotExists", true);
                session.setAttribute("user", null);
            }
        }
        return null;
    }
}
