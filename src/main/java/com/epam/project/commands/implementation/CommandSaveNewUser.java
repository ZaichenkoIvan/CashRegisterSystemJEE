package com.epam.project.commands.implementation;

import com.epam.project.commands.Command;
import com.epam.project.config.Configuration;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ExecutionResult;
import com.epam.project.controller.SessionRequestContent;
import com.epam.project.domain.User;
import com.epam.project.domain.UserCart;
import com.epam.project.domain.UserRole;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

public class CommandSaveNewUser implements Command {

    private static final Logger LOGGER = Logger.getLogger(CommandSaveNewUser.class);
    private UserService userServ;

    public CommandSaveNewUser(UserService userServ) {
        this.userServ = userServ;
    }

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            String login = content.getRequestParameter("name")[0];
            String password = content.getRequestParameter("password")[0];
            String phone = content.getRequestParameter("phone")[0];
            String email = content.getRequestParameter("email")[0];
            String address = content.getRequestParameter("address")[0];
            String notes = content.getRequestParameter("notes")[0];
            User user = new User(login, password);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            user.setAddress(address);
            user.setNotes(notes);
            user.setUserRole(UserRole.USER);
            if (userServ.addUser(user)) {
                result.addSessionAttribute("cart", new UserCart(user.getName()));
                result.addSessionAttribute("user", userServ.findUser(login, password));
                result.setPage(conf.getPage("redirect_home"));
            } else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveNewUserErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        } catch (Exception e) {
            LOGGER.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveNewUserErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
