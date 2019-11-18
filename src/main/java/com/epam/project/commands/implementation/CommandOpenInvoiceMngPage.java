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

import java.util.LinkedList;
import java.util.List;

public class CommandOpenInvoiceMngPage implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandOpenInvoiceMngPage.class);
    private InvoiceService serv;

    public CommandOpenInvoiceMngPage(InvoiceService serv) {
        this.serv = serv;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.CASHIER, UserRole.SENIOR_CASHIER, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            String type = (String) content.getRequestParameter("type")[0];
            List<Invoice> invoices = new LinkedList<>();
            if (type.equals("all"))
                invoices = serv.findAllInvoices();
            if (type.equals("new"))
                invoices = serv.findNewInvoices();
            if (type.equals("cancelled"))
                invoices = serv.findCancelledInvoices();
            if (type.equals("closed"))
                invoices = serv.findFinishedInvoices();
            result.addRequestAttribute("invoices", invoices);
            result.setPage(conf.getPage("manageInvoices"));
        } catch (Exception e) {
            LOGGER.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("manageInvoicesErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}