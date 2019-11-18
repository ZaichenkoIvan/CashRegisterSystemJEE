package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserCart;
import com.epam.project.exceptions.InvoiceServiceRuntimeException;
import com.epam.project.service.InvoiceService;
import org.apache.log4j.Logger;

public class CommandCreateInvoiceAndPay implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandCreateInvoiceAndPay.class);
    private InvoiceService invoiceServ;

    public CommandCreateInvoiceAndPay(InvoiceService invoiceServ) {
        this.invoiceServ = invoiceServ;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            Long invoiceCode = System.currentTimeMillis();
            UserCart cart = (UserCart) content.getSessionAttribute("cart");
            Invoice invoice = invoiceServ.createInvoiceFromUserCart(cart, invoiceCode);
            if (invoiceServ.addInvoice(invoice) && invoiceServ.payByInvoice(invoiceCode)) {
                cart.removeAll();
                result.setPage(conf.getPage("redirect_home"));
            } else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("invoiceCreationErr"));
                result.setPage(conf.getPage("error"));
            }
        } catch (NullPointerException | InvoiceServiceRuntimeException npe) {
            LOGGER.error(npe);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("invoiceCreationErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
