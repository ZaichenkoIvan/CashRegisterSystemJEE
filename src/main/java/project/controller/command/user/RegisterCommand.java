package project.controller.command.user;

import project.controller.command.Command;
import project.model.domain.User;
import project.model.entity.enums.Role;
import project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RegisterCommand implements Command {
    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        final String name = request.getParameter("name");
        final String email = request.getParameter("email");
        final String surname = request.getParameter("surname");

        final String password = request.getParameter("password");
        final String passwordConfirm = request.getParameter("confirmPassword");
        if(!Objects.equals(password, passwordConfirm)){
            return "login.jsp";
        }

        User user = User.builder()
                .withEmail(email)
                .withName(name)
                .withSurname(surname)
                .withPassword(password)
                .withRole(Role.USER)
                .build();
        userService.register(user);
        return "login.jsp";
    }
}
