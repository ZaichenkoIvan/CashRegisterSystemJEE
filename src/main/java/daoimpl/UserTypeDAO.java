package main.java.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.dao.PoolConnection;
import org.apache.log4j.Logger;

import main.java.dao.IUserTypeDAO;
import main.java.entity.UserType;

public class UserTypeDAO implements IUserTypeDAO<UserType> {
	
	private static UserTypeDAO instance;
	private static Logger logger = Logger.getLogger(GoodsDAO.class);
	private PoolConnection poolConnection;
	
	private UserTypeDAO(PoolConnection poolConnection) {
		this.poolConnection = poolConnection;
	}
	
	public static IUserTypeDAO<UserType> getInstance(PoolConnection poolConnection) {
		if (instance == null) {
			instance = new UserTypeDAO(poolConnection);
		}
		return instance;		
	}
	
	@Override
	public Long insert(UserType usertype) {

		try (Connection connection = poolConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO user_type "
					+ "(type, description) values(?, ?)")) {
			statement.setString(1, usertype.getType());
			statement.setString(2, usertype.getDescription());			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}

	@Override
	public void update(UserType usertype) {
		if (usertype != null) {
			try(Connection connection = poolConnection.getConnection();
					PreparedStatement statement = connection.prepareStatement("UPDATE user_type SET type=?, description=? WHERE id=?")) {
				statement.setString(1, usertype.getType());
				statement.setString(2, usertype.getDescription());
				statement.setLong(3, usertype.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	@Override
	public void delete(UserType usertype) {		
		if (usertype != null) {			
			try(Connection connection = poolConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM user_type WHERE id = ?")) {
				statement.setLong(1, usertype.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}
	
	@Override
	public Long findUserType(String type) {		
		try (Connection connection = poolConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM user_type WHERE type = ?")) {
			statement.setString(1, type);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getLong("id");	
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
}