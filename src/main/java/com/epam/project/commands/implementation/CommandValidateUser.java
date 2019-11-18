package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.User;
import com.epam.project.domain.UserCart;
import com.epam.project.exceptions.UnknownUserRuntimeException;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

public class CommandValidateUser implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandValidateUser.class);
    private UserService serv;

    public CommandValidateUser(UserService serv) {
        this.serv = serv;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        String login = content.getRequestParameter("name")[0];
        String password = content.getRequestParameter("password")[0];
        try {
            User user = serv.findUser(login, password);
            UserCart cart = new UserCart(user.getName());
            result.addSessionAttribute("user", user);
            result.addSessionAttribute("cart", cart);
            result.setPage(conf.getPage("redirect_home"));
        } catch (UnknownUserRuntimeException uue) {
            LOGGER.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("validateUserErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
