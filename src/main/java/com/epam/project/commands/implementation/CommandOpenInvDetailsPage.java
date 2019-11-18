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
import com.epam.project.service.ProductService;
import org.apache.log4j.Logger;

import java.util.Set;

public class CommandOpenInvDetailsPage implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandOpenInvDetailsPage.class);
    private InvoiceService serv;
    private ProductService prodServ;

    public CommandOpenInvDetailsPage(InvoiceService serv, ProductService prodServ) {
        this.serv = serv;
        this.prodServ = prodServ;
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
            Long code = Long.parseLong(content.getRequestParameter("code")[0]);
            Invoice invoice = serv.findInvoiceByOrderNumber(code);
            Set<String> products = prodServ.createProductSet();
            result.addRequestAttribute("invoice", invoice);
            result.addRequestAttribute("products", products);
            result.setPage(conf.getPage("invoiceDetails"));
        } catch (Exception e) {
            LOGGER.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showInvoiceDetailsErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
