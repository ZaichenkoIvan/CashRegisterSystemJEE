package main.java.command.impl;

import main.java.command.Command;
import main.java.entity.Check;
import main.java.entity.Checkspec;
import main.java.service.CheckService;
import main.java.service.Report;
import main.java.service.ReportService;

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
        String url = null;
        HttpSession session = req.getSession();
        if (req.getParameter("btnSearchCheck") != null) {
            Long checkid = Long.valueOf(req.getParameter("checkid"));
            Check check = checkService.findById(checkid);
            List<Checkspec> checkspecs = checkService.findAllCheckspecByCheckId(checkid);
            if (check != null) {
                session.setAttribute("check", check);
                session.setAttribute("checkspecs", checkspecs);
                session.setAttribute("checkfind", null);
            } else {
                session.setAttribute("check", null);
                session.setAttribute("checkfind", checkid);
            }
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
            Report zReport;
            zReport = reportService.getDataZReport();
            session.setAttribute("xReport", null);
            session.setAttribute("zReport", zReport);

            url = "report";
        }
        return url;
    }
}