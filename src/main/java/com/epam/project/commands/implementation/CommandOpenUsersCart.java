package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.UserCart;
import com.epam.project.domain.UserCartView;
import com.epam.project.exceptions.InvoiceServiceRuntimeException;
import com.epam.project.service.InvoiceService;
import org.apache.log4j.Logger;

public class CommandOpenUsersCart implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandOpenUsersCart.class);
    private InvoiceService serv;

    public CommandOpenUsersCart(InvoiceService serv) {
        this.serv = serv;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            UserCartView view = serv.createUsersCartView((UserCart) content.getSessionAttribute("cart"));
            result.addRequestAttribute("cartView", view);
            result.setPage(conf.getPage("usersCart"));
        } catch (NullPointerException | InvoiceServiceRuntimeException uue) {
            LOGGER.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showUserCartErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }

}
