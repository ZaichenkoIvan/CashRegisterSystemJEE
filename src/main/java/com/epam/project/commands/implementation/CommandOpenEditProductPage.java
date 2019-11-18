package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Product;
import com.epam.project.service.ProductService;
import org.apache.log4j.Logger;

public class CommandOpenEditProductPage implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandOpenEditProductPage.class);
    private ProductService serv;

    public CommandOpenEditProductPage(ProductService serv) {
        this.serv = serv;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration config = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            String code = content.getRequestParameter("productCode")[0];
            Product product = serv.findProductByCode(code);
            result.addRequestAttribute("product", product);
            result.setPage(config.getPage("editProduct"));
        } catch (Exception e) {
            LOGGER.error(e);
            result.addRequestAttribute("errorMessage", config.getErrorMessage("editProductPageErr"));
            result.setPage(config.getPage("error"));
        }
        return result;
    }
}
