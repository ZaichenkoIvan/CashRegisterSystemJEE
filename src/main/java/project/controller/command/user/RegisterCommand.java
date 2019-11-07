package project.controller.command.user;

import project.controller.command.Command;
import project.model.domain.User;
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
        final String name = (String) request.getAttribute("name");
        final String email = (String) request.getAttribute("email");

        final String password1 = (String) request.getAttribute("password1");
        final String password2 = (String) request.getAttribute("password2");
        if(!Objects.equals(password1, password2)){
            return "view/register.jsp";
        }

        User user = User.builder()
                .withEmail(email)
                .withName(name)
                .withPassword(password1)
                .build();
        userService.register(user);
        return "view/login.jsp";
    }
}
