package com.epam.project.commands.implementation;

import com.epam.project.commands.DataValidator;
import com.epam.project.commands.Command;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Invoice;
import com.epam.project.domain.InvoiceStatus;
import com.epam.project.domain.Payment;
import com.epam.project.exceptions.InvalidValueRuntimeException;
import com.epam.project.service.InvoiceService;
import com.epam.project.service.PaymentService;
import com.epam.project.service.ProductService;
import org.apache.log4j.Logger;

import java.util.Set;

public class CommandAddNewPayment implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandAddNewPayment.class);
    private InvoiceService invserv;
    private ProductService prodServ;
    private PaymentService serv;

    public CommandAddNewPayment(InvoiceService invserv, ProductService prodServ, PaymentService serv) {
        this.invserv = invserv;
        this.prodServ = prodServ;
        this.serv = serv;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);

        try {
            Payment payment = new Payment();
            payment.setProductCode(content.getRequestParameter("productCode")[0]);
            payment.setQuantity(DataValidator.filterDouble(content.getRequestParameter("quantity")[0]));
            payment.setStatusId(InvoiceStatus.CREATED);
            payment.setOrderCode(Long.parseLong(content.getRequestParameter("orderCode")[0]));

            if (serv.addPayment(payment)) {
                Invoice invoice = invserv.findInvoiceByOrderNumber(payment.getOrderCode());
                Set<String> products = prodServ.createProductSet();
                result.addRequestAttribute("invoice", invoice);
                result.addRequestAttribute("products", products);
                result.setPage(conf.getPage("invoiceDetails"));
            } else {
                result.setDirection(Direction.FORWARD);
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("addNewPaymentErr"));
                result.setPage(conf.getPage("error"));
            }

        } catch (InvalidValueRuntimeException ive) {
            LOGGER.error(ive);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("dataValidationError"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }

        return result;
    }
}
