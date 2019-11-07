package project.controller.command.show;

import project.controller.command.Command;
import project.model.domain.Order;
import project.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OrderShowCommand implements Command {
    private OrderService orderService;

    public OrderShowCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int currentPage = Integer.valueOf(request.getParameter("currentPage"));
        int recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));

        List<Order> orders = orderService.findAll(currentPage, recordsPerPage);

        request.setAttribute("orders", orders);

        int rows = orderService.getNumberOfRows();

        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        final String command = request.getParameter("commandShow");
        request.setAttribute("showOrders", command);

        return "listOrders.jsp";
    }
}
