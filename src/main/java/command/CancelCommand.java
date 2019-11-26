package main.java.command;

import java.util.List;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import main.java.entity.*;
import main.java.dao.*;
import main.java.service.*;

public class CancelCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String url = null;
		HttpSession session = req.getSession();
		if (req.getParameter("btnSearchCheck") != null) {
			Long checkid = Long.valueOf(req.getParameter("checkid"));
			Check check = CheckService.findById(checkid);
			List<Checkspec> checkspecs = CheckService.findAllCheckspecByCheckId(checkid);
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
			Check check = (Check)session.getAttribute("check");
			if (check != null) {
				CheckService.cancelCheck(check);
				session.setAttribute("check", check);
			}
			url = "cancel";
		} else if (req.getParameter("btnCancelCheckspec") != null) {
			Check check = (Check)session.getAttribute("check");
			String specnumber = req.getParameter("checkspecnum");
			if (check != null && specnumber !=null && !specnumber.isEmpty()) {
				int checkspecnum = Integer.valueOf(req.getParameter("checkspecnum"));
				@SuppressWarnings("unchecked")
				List<Checkspec> checkspecs = (List<Checkspec>) session.getAttribute("checkspecs");
				if (checkspecs != null && checkspecs.size() >= checkspecnum) {
					CheckService.cancelCheckSpec(checkspecs, checkspecnum, check);
				}
			}
			url = "cancel";
		}
		if (req.getParameter("btnXReport") != null) {
			Report xReport = DAOFactory.getServiceUstil().getDataReport();
			session.setAttribute("xReport", xReport);
			session.setAttribute("zReport", null);
			url = "report";
		} else if (req.getParameter("btnZReport") != null) {
			Report zReport;
				zReport = DAOFactory.getServiceUstil().getDataZReport();
				session.setAttribute("xReport", null);
				session.setAttribute("zReport", zReport);

			url = "report";
		}
		return url;
	}
}
