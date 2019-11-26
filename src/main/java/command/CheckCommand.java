package main.java.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import main.java.entity.Checkspec;
import main.java.entity.User;
import main.java.service.CheckService;
import main.java.service.GoodsService;

public class CheckCommand implements Command {

    private static Logger logger = Logger.getLogger(GoodsService.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("addcheckspecs");
        if (req.getParameter("btnCreateCheck") != null && checkspecs != null && checkspecs.size() > 0) {
            CheckService.addCheck((User) session.getAttribute("user"), checkspecs);
            req.setAttribute("addedCheck", true);
            //session.setAttribute("addcheckspecs", null);
            checkspecs.clear();
        }
        if (req.getParameter("btnCancelCheck") != null && checkspecs != null) {
            checkspecs.clear();
        }
        return null;
    }
}
