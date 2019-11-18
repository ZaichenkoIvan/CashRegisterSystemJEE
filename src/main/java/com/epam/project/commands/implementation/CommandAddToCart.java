package com.epam.project.commands.implementation;

import com.epam.project.commands.DataValidator;
import com.epam.project.commands.Command;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.UserCart;
import com.epam.project.exceptions.InvalidValueRuntimeException;
import org.apache.log4j.Logger;

public class CommandAddToCart implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandAddToCart.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        Configuration config = Configuration.getInstance();
        result.setDirection(Direction.FORWARD);

        try {
            UserCart cart = (UserCart) content.getSessionAttribute("cart");
            String productCode = content.getRequestParameter("productCode")[0];
            String productQuantity = content.getRequestParameter("productQuantity")[0];
            Double quantity = DataValidator.filterDouble(productQuantity);
            cart.addProduct(productCode, quantity);
            result.addSessionAttribute("cart", cart);
            result.setPage(config.getPage("redirect_home"));

        } catch (InvalidValueRuntimeException ive) {
            LOGGER.error(ive);
            result.addRequestAttribute("errorMessage", config.getErrorMessage("dataValidationError"));
            result.setPage(config.getPage("error"));
        }

        return result;
    }
}
