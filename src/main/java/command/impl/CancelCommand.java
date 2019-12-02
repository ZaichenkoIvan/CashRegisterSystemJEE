package command.impl;

import command.Command;
import domain.Check;
import domain.Checkspec;
import domain.Report;
import service.CheckService;
import service.ReportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CancelCommand implements Command {

    private final CheckService checkService;
    private final ReportService reportService;

    public CancelCommand(CheckService checkService, ReportService reportService) {
        this.checkService = checkService;
        this.reportService = reportService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String url =null;
        HttpSession session = req.getSession();
        if (req.getParameter("btnSearchCheck") != null) {
            Long checkid = Long.valueOf(req.getParameter("checkid"));
            Check check = checkService.findById(checkid);
            List<Checkspec> checkspecs = checkService.findAllCheckspecByCheckId(checkid);
                session.setAttribute("check", check);
                session.setAttribute("checkspecs", checkspecs);
                session.setAttribute("checkfind", null);
            url = "cancel";
        }
        if (req.getParameter("btnCancelCheck") != null) {
            Check check = (Check) session.getAttribute("check");
            if (check != null) {
                checkService.cancelCheck(check);
                session.setAttribute("check", check);
            }
            url = "cancel";
        } else if (req.getParameter("btnCancelCheckspec") != null) {
            Check check = (Check) session.getAttribute("check");
            String specnumber = req.getParameter("checkspecnum");
            if (check != null && specnumber != null && !specnumber.isEmpty()) {
                int checkspecnum = Integer.valueOf(req.getParameter("checkspecnum"));
                @SuppressWarnings("unchecked")
                List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("checkspecs");
                if (checkspecs != null && checkspecs.size() >= checkspecnum) {
                    checkService.cancelCheckSpec(checkspecs, checkspecnum, check);
                }
            }
            url = "cancel";
        }

        if (req.getParameter("btnXReport") != null) {
            Report xReport = reportService.getDataReport();
            session.setAttribute("xReport", xReport);
            session.setAttribute("zReport", null);
            url = "report";
        } else if (req.getParameter("btnZReport") != null) {
            Report zReport = reportService.getDataZReport();
            session.setAttribute("xReport", null);
            session.setAttribute("zReport", zReport);

            url = "report";
        }
        return url;
    }
}
