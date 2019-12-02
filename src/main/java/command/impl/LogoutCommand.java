package command.impl;

import command.Command;

import javax.servlet.http.*;

public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		javax.servlet.http.HttpSession session = req.getSession();
		String lang = (String)session.getAttribute("language");
    	session.invalidate();
		if (lang != null) {
			req.getSession().setAttribute("language", lang);
		}
		return "login";
	}
}
