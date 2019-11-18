package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserRole;
import com.epam.project.service.InvoiceService;
import org.apache.log4j.Logger;

public class CommandCancelInvoice implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandCancelInvoice.class);
    private InvoiceService serv;

    public CommandCancelInvoice(InvoiceService serv) {
        this.serv = serv;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);

        if (!Security.checkSecurity(content, UserRole.CASHIER, UserRole.SENIOR_CASHIER, UserRole.ADMIN)) {
            result.setPage(conf.getPage("securityError"));
            return result;
        }
        result.setPage(conf.getPage("administration"));
        Long invCode = Long.parseLong(content.getRequestParameter("invCode")[0]);

        if (serv.cancelInvoice(invCode)) {
            Invoice invoice = serv.findInvoiceByOrderNumber(invCode);
            result.addRequestAttribute("invoice", invoice);
            result.setPage(conf.getPage("invoiceDetails"));
        } else {
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("cancelInvoiceErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }

        return result;
    }
}
