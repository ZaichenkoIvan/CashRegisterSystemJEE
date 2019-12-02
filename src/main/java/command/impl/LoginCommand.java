package main.java.command.impl;

import main.java.command.Command;
import main.java.domain.User;
import main.java.domain.UserType;
import main.java.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = userService.findUser(req.getParameter("email"), req.getParameter("password"));
        session.setAttribute("userNotExists", null);
        session.setAttribute("user", user);

        UserType type = user.getUserType();
        if (type.getType().equalsIgnoreCase("goods_spec")) {
            return "goods";
        } else if (type.getType().equalsIgnoreCase("senior_cashier")) {
            return "cancel";
        } else {
            return "check";
        }
    }
}
