package main.java.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.dao.PoolConnection;
import org.apache.log4j.Logger;

import main.java.dao.IUserDAO;
import main.java.entity.User;
import main.java.entity.UserType;

public class UserDAO implements IUserDAO<User> {

	private static UserDAO instance;
	private static Logger logger = Logger.getLogger(UserDAO.class);
	private PoolConnection poolConnection;

	private UserDAO(PoolConnection poolConnection) {
		this.poolConnection = poolConnection;
	}
	
	public static IUserDAO<User> getInstance(PoolConnection poolConnection) {
		if (instance == null) {
			instance = new UserDAO(poolConnection);
		}
		return instance;		
	}
	
	@Override
	public Long insert(User user) {
		if (user != null) {
			try (Connection connection = poolConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO user "
						+ "(login, password, name, id_user_type) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, user.getLogin());
				statement.setString(2, user.getPassword());
				statement.setString(3, user.getName());
				statement.setLong(4, user.getIdUserType());
				statement.executeUpdate();
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
				return rs.getLong(1);
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return null;
	}

	@Override
	public void update(User user) {
		if (user != null) {
			try(Connection connection = poolConnection.getConnection();
					PreparedStatement statement = connection.prepareStatement("UPDATE user SET login=?, password=?, name=?, id_user_type=? WHERE id=?")) {
				statement.setString(1, user.getLogin());
				statement.setString(2, user.getPassword());
				statement.setString(3, user.getName());
				statement.setLong(4, user.getIdUserType());
				statement.setLong(5, user.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	@Override
	public void delete(User user) {
		if (user != null) {
			try(Connection connection = poolConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
				statement.setLong(1, user.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}
	
	@Override
	public User findUserByLogin(String login) {
		try (Connection connection = poolConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE login = ?")) {
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();			
			if (resultSet.first()) {
				User user = new User();
				user.setId(resultSet.getLong("id"));
				user.setLogin(login);
				user.setIdUserType(resultSet.getLong("id_user_type"));
				user.setName(resultSet.getString("name"));
				return user;
			}
		} catch (SQLException e) {
			logger.error("Ошибка при поиске пользователя", e);
		}
		return null;
	}
	
	@Override
	public User findUser(String login, String password) {
		try (Connection connection = poolConnection.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT u.*, t.id AS tid, t.type, t.description FROM user u "
					+ "INNER JOIN user_type t ON t.id = u.id_user_type "
					+ "WHERE u.login = ? AND u.password = ? ")) {
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();			
			if (resultSet.first()) {
				User user = new User();
				UserType userType = new UserType();
				user.setId(resultSet.getLong("id"));
				user.setLogin(login);
				user.setIdUserType(resultSet.getLong("id_user_type"));
				user.setName(resultSet.getString("name"));
				userType.setId(resultSet.getLong("tid"));
				userType.setType(resultSet.getString("type"));
				userType.setDescription(resultSet.getString("description"));
				user.setUserType(userType);
				return user;
			}
		} catch (SQLException e) {
			logger.error("Ошибка при поиске пользователя", e);
		}
		return null;
	}
}
