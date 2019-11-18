package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.ProductServiceRuntimeException;
import com.epam.project.service.ProductService;
import org.apache.log4j.Logger;

import java.util.List;

public class CommandOpenMainPage implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandOpenMainPage.class);
    private ProductService productServ;

    public CommandOpenMainPage(ProductService productServ) {
        this.productServ = productServ;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            Integer totalPages = (int) Math.floor(productServ.calculateProductsNumber() / 5) + 1;
            Integer pageNum = content.checkRequestParameter("pageNum") ?
                    Integer.parseInt(content.getRequestParameter("pageNum")[0]) : 1;
            List<Product> products = productServ.findProducts((pageNum - 1) * 5, 5);
            result.addRequestAttribute("products", products);
            result.addRequestAttribute("totalPages", totalPages);
            result.addRequestAttribute("pageNum", pageNum);
            result.setPage(conf.getPage("main"));
        } catch (ProductServiceRuntimeException uue) {
            LOGGER.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showMainPageErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
