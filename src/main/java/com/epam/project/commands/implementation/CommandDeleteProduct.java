package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.UserRole;
import com.epam.project.service.ProductService;
import org.apache.log4j.Logger;

public class CommandDeleteProduct implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandDeleteProduct.class);

    private ProductService serv;

    public CommandDeleteProduct(ProductService serv) {
        this.serv = serv;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.MERCHANT, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            String productCode = (String) content.getRequestParameter("productCode")[0];
            if (serv.deleteProduct(productCode)) {
                result.setPage(conf.getPage("redirect_manageProducts"));
            } else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("deleteProductErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        } catch (Exception uue) {
            LOGGER.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("deleteProductErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}