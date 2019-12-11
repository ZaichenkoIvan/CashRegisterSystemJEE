package command.impl;

import command.Command;
import domain.User;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        User user = userService.registration(req.getParameter("name"), req.getParameter("email"), req.getParameter("password"));
        if (user != null) {
            req.getSession().setAttribute("user", user);
            return "check";
        } else {
            req.setAttribute("existsLogin", req.getParameter("email"));
            return null;
        }
    }
}
