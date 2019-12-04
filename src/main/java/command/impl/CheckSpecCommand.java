package command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import command.Command;
import domain.Checkspec;
import service.CheckService;

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
        String xcode = req.getParameter("xcode");
        String xname = req.getParameter("xname");
        try {
            Double quant = Double.valueOf(req.getParameter("quant"));
            Checkspec spec = checkService.addCheckSpec(xname, xcode, quant, req.getParameter("nds"));
            if (spec != null) {
                checkspecs.add(spec);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("wronginput", true);
        }
        return null;
    }
}
