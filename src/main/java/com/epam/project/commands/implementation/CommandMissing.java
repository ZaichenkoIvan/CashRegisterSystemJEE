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

import java.util.Locale;

public class CommandMissing implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandMissing.class);
    private UserService userServ;

    public CommandMissing(UserService userServ) {
        this.userServ = userServ;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            User guest = userServ.findUser("Guest", "");
            UserCart cart = new UserCart(guest.getName());
            if (!content.checkSessionAttribute("user"))
                result.addSessionAttribute("user", guest);
            if (!content.checkSessionAttribute("cart"))
                result.addSessionAttribute("cart", cart);
            if (!content.checkSessionAttribute("local"))
                result.addSessionAttribute("locale", new Locale("ru", "RU"));
            result.addRequestAttribute("pageNum", 1);
            result.setPage(conf.getPage("redirect_home"));
        } catch (UnknownUserRuntimeException uue) {
            LOGGER.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("generalErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
