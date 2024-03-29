package command.impl;

import command.Command;
import domain.Checkspec;
import domain.User;
import service.CheckService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CheckCommand implements Command {
    private final CheckService checkService;

    public CheckCommand(CheckService checkService) {
        this.checkService = checkService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("addcheckspecs");
        if (req.getParameter("btnCreateCheck") != null) {
            checkService.addCheck((User) session.getAttribute("user"), checkspecs);
            req.setAttribute("addedCheck", true);
            checkspecs.clear();
        }
        return null;
    }
}
