package project.controller.command.show;

import project.controller.command.Command;
import project.model.domain.Invoice;
import project.model.service.InvoiceService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InvoiceShowCommand implements Command {
    private InvoiceService invoiceService;

    public InvoiceShowCommand(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int currentPage = Integer.valueOf(request.getParameter("currentPage"));
        int recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));

        List<Invoice> invoices = invoiceService.findAll(currentPage, recordsPerPage);
        request.setAttribute("invoices", invoices);

        int rows = invoiceService.getNumberOfRows();
        int nOfPages = rows / recordsPerPage;
        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("currentPage", currentPage);
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        final String command = request.getParameter("command");
        request.setAttribute("showInvoices", command);

        return "admin/listInvoices.jsp";
    }
}
