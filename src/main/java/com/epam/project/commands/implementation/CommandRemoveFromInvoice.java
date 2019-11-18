package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.ProductServiceRuntimeException;
import com.epam.project.service.InvoiceService;
import com.epam.project.service.ProductService;
import org.apache.log4j.Logger;

import java.util.Set;

public class CommandRemoveFromInvoice implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandRemoveFromInvoice.class);
    private InvoiceService serv;
    private ProductService prodServ;

    public CommandRemoveFromInvoice(InvoiceService serv, ProductService prodServ) {
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
            Long invCode = Long.parseLong(content.getRequestParameter("invCode")[0]);
            String productCode =  content.getRequestParameter("productCode")[0];
            if (serv.removeProductFromInvoice(invCode, productCode)) {
                Invoice inv = serv.findInvoiceByOrderNumber(invCode);
                Set<String> products = prodServ.createProductSet();
                result.addRequestAttribute("invoice", inv);
                result.addRequestAttribute("products", products);
                result.addRequestAttribute("command", "showInvoiceDetail");
                result.addRequestAttribute("code", invCode);
                result.setPage(conf.getPage("invoiceDetails"));
            } else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("removeProductFromInvoiceErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        } catch (ProductServiceRuntimeException | NullPointerException npe) {
            LOGGER.error(npe);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("removeProductFromInvoiceErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
