package main.java.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import main.java.command.Command;
import main.java.entity.*;
import main.java.service.CheckService;
import main.java.service.impl.CheckServiceImpl;

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
			Checkspec spec = checkService.addCheckSpec(xcode, xname, quant, req.getParameter("nds"));
			if (spec != null) {
				checkspecs.add(spec);
			} else {
				if (xcode != null && !xcode.isEmpty()) {
					req.setAttribute("goodsCodeNotFound", xcode);
				} else {
					req.setAttribute("goodsNameNotFound", xname);
				}
			}
		} catch (NumberFormatException e) {
			req.setAttribute("wronginput", true);
		}
		return null;				
	}
}
