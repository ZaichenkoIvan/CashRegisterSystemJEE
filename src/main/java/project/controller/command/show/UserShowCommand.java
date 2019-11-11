package project.controller.command.show;

import project.controller.command.Command;
import project.model.domain.User;
import project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserShowCommand implements Command {
    private UserService userService;

    public UserShowCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int currentPage = Integer.valueOf(request.getParameter("currentPage"));
        int recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));

        List<User> users = userService.findAll(currentPage, recordsPerPage);

        request.setAttribute("users", users);

        int rows = userService.getNumberOfRows();
        int nOfPages = rows / recordsPerPage;
        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        final String command = request.getParameter("command");
        request.setAttribute("showUsers", command);

        return "listUsers.jsp";
    }
}
