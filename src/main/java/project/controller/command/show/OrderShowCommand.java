package project.controller.command.show;

import project.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class OrderShowCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "listOrders.jsp";
    }
}
