package project.controller.command.show;

import project.controller.command.Command;
import project.model.domain.Payment;
import project.model.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PaymentShowCommand implements Command {
    private PaymentService paymentService;

    public PaymentShowCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int currentPage = Integer.valueOf(request.getParameter("currentPage"));
        int recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));

        List<Payment> payments = paymentService.findAll(currentPage, recordsPerPage);

        request.setAttribute("payments", payments);

        int rows = paymentService.getNumberOfRows();

        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        final String command = request.getParameter("command");
        request.setAttribute("showPayments", command);

        return "listPayments.jsp";
    }
}
