package main.java.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import main.java.command.Command;
import main.java.domain.Checkspec;
import main.java.service.CheckService;

public class CheckSpecCommand implements Command {
    private final CheckService checkService;

    public CheckSpecCommand(CheckService checkService) {
        this.checkService = checkService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("addcheckspecs");
        if (checkspecs == null) {
            checkspecs = new ArrayList<>();
            session.setAttribute("addcheckspecs", checkspecs);
        }
        Integer xcode = Integer.valueOf(req.getParameter("xcode"));
        try {
            Double quant = Double.valueOf(req.getParameter("quant"));
            Checkspec spec = checkService.addCheckSpec(xcode, quant, req.getParameter("nds"));
            if (spec != null) {
                checkspecs.add(spec);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("wronginput", true);
        }
        return null;
    }
}
