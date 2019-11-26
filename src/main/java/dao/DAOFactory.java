package main.java.dao;

import main.java.daoimpl.*;
import main.java.entity.*;
import main.java.service.ServiceUtil;

public class DAOFactory {
	private static PoolConnection poolConnection = new PoolConnection();
	public enum Factories {
		MYSQL
	}
	
	public static IUserDAO<User> getUserDAO() {
		return UserDAO.getInstance(poolConnection);
	}
	
	public static IUserTypeDAO<UserType> getUserTypeDAO() {
		return UserTypeDAO.getInstance(poolConnection);
	}
	
	public static IGoodsDAO<Goods> getGoodsDAO() {
		return GoodsDAO.getInstance(poolConnection);
	}
	
	public static ICheckDAO<Check> getCheckDAO() {
		return CheckDAO.getInstance(poolConnection);
	}

	public static ICheckSpecDAO<Checkspec> getCheckSpecDAO() {
		return CheckSpecDAO.getInstance(poolConnection);
	}
	
	public static IFiscalDAO<Fiscal> getFiscalDAO() {
		return FiscalDAO.getInstance(poolConnection);
	}

	public static ServiceUtil getServiceUstil(){return new ServiceUtil(poolConnection);}
}
