package main.java.service;

import main.java.dao.DAOFactory;
import main.java.dao.IUserDAO;
import main.java.dao.IUserTypeDAO;
import main.java.entity.User;
import main.java.entity.UserType;

public class UserService {

	public static User findUser(String login, String password) {
		
		IUserDAO<User> userDAO = DAOFactory.getUserDAO();
		return userDAO.findUser(login, password);
	}

	public static User registration(String userName, String login, String password) {
		
		IUserDAO<User> userDAO = DAOFactory.getUserDAO();
		IUserTypeDAO<UserType> userTypeDao = DAOFactory.getUserTypeDAO();
		if (userDAO.findUserByLogin(login) != null) {
			return null;
		}
		User user = new User();
		user.setName(userName);
		user.setLogin(login);
		user.setPassword(password);
		user.setIdUserType(userTypeDao.findUserType("cashier"));	//по-умолчанию при регистрации права кассира
		userDAO.insert(user);
		user.setPassword(null);
		return user;		
	}
}
