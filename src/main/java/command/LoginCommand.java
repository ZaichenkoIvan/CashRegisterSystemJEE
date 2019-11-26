package main.java.command;

import javax.servlet.http.*;

import org.apache.log4j.Logger;

import main.java.entity.User;
import main.java.entity.UserType;
import main.java.service.UserService;

public class LoginCommand implements Command {

	private static Logger logger = Logger.getLogger(LoginCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		User dbUser = UserService.findUser(req.getParameter("email"),  req.getParameter("password"));
		if (dbUser != null) {
			session.setAttribute("userNotExists", null);
			session.setAttribute("user", dbUser);
			logger.info("Авторизация пользователя " + dbUser.getName());
			UserType type = dbUser.getUserType();
			if (type.getType().equalsIgnoreCase("goods_spec")) {			//товаровед
				return "goods";
			} else if (type.getType().equalsIgnoreCase("cashier")) {		//кассир
				return "check";
 			} else if (type.getType().equalsIgnoreCase("senior_cashier")) {	//старший кассир
				return "cancel";
			}			
		} else {
			if (session != null) {
				session.setAttribute("userNotExists", true);
				session.setAttribute("user", null);
			}
		}
		return null;
	}
}
