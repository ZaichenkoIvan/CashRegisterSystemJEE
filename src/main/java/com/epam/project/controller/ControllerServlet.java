package com.epam.project.controller;

import com.epam.project.commands.Command;
import com.epam.project.commands.implementation.CommandMissing;
import com.epam.project.config.ApplicationContextInjector;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
//TODO rename
public class ControllerServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ControllerServlet.class);
    private Map<String, Command> commandNameToCommand;
    private Command defaultCommand;

    @Override
    public void init(){
        ApplicationContextInjector injector = ApplicationContextInjector.getInstance();
        this.commandNameToCommand = injector.getCommands();
        this.defaultCommand = new CommandMissing(ApplicationContextInjector.getUserService());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionRequestContent content = new SessionRequestContent(req);
        //LOGGER.info(content);
        Command command = commandNameToCommand.getOrDefault(req.getParameter("command"), defaultCommand);
        ExecutionResult result = command.execute(content);
        if (result.isInvalidated())
            req.getSession(false).invalidate();
        result.updateRequest(req);
        if (result.getDirection() == Direction.FORWARD)
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
        if (result.getDirection() == Direction.REDIRECT) {
            resp.sendRedirect(result.getPage());
        }
    }
}
