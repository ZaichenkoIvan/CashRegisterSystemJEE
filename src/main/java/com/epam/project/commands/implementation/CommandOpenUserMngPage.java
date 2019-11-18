package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.commands.Security;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class CommandOpenUserMngPage implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandOpenUserMngPage.class);

    private UserService serv;

    public CommandOpenUserMngPage(UserService serv) {
        this.serv = serv;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            String type = content.getRequestParameter("type")[0];
            List<User> users = new LinkedList<>();
            if (type.equals("all"))
                users = serv.findAllUsers();
            if (type.equals("user"))
                users = serv.findUsersByRole(UserRole.USER);
            if (type.equals("cashier"))
                users = serv.findUsersByRole(UserRole.CASHIER);
            if (type.equals("seniorCashier"))
                users = serv.findUsersByRole(UserRole.SENIOR_CASHIER);
            if (type.equals("merchant"))
                users = serv.findUsersByRole(UserRole.MERCHANT);
            if (type.equals("admin"))
                users = serv.findUsersByRole(UserRole.ADMIN);
            result.addRequestAttribute("users", users);
            result.setPage(conf.getPage("manageUsers"));
        } catch (Exception e) {
            LOGGER.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("manageUsersErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}