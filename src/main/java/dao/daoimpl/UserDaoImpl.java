package main.java.dao.daoimpl;

import main.java.dao.AbstractGenericDao;
import main.java.dao.PoolConnection;
import main.java.dao.UserDao;
import main.java.entity.User;
import main.java.entity.UserType;
import main.java.exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractGenericDao<User> implements UserDao {
    private static final String INSERT = "INSERT INTO user" +
            "(login, password, name, id_user_type) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE user SET login=?, password=?, name=?, id_user_type=? WHERE id=?";
    private static final String FIND_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
//    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT u.*, t.id AS tid, t.type, t.description FROM user u" +
//            "INNER JOIN user_type t ON t.id = u.id_user_type" +
//            "WHERE u.login = ? AND u.password = ?";
        private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user " +
            "INNER JOIN user_type ON user_type.id = user.id_user_type " +
            "WHERE user.login = ? AND user.password = ?";

    public UserDaoImpl(PoolConnection poolConnection) {
        super(poolConnection);
    }

    @Override
    protected void setInsertProperties(PreparedStatement statement, User user) {
        try {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setLong(4, user.getIdUserType());
        } catch (SQLException e) {
            LOGGER.error("Not insert user in db", e);
            throw new DatabaseRuntimeException("Not insert user in db", e);
        }
    }

    @Override
    protected void setUpdateProperties(PreparedStatement statement, User user) {
        setInsertProperties(statement, user);
        try {
            statement.setLong(5, user.getId());
        } catch (SQLException e) {
            LOGGER.error("Not update user in db", e);
            throw new DatabaseRuntimeException("Not update user in db", e);
        }
    }

    @Override
    protected User parseToOne(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getLong("id"));
            user.setLogin(resultSet.getString("login"));
            user.setIdUserType(resultSet.getLong("id_user_type"));
            user.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.error("Not find user in db", e);
            throw new DatabaseRuntimeException("Not find user in db", e);
        }

        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        throw new UnsupportedOperationException("Not implementation yet");
    }

    @Override
    public Long insert(User user) {
        return insert(user, INSERT);
    }

    @Override
    public void update(User user) {
        update(user, UPDATE);
    }

    @Override
    public User findUserByLogin(String login) {
        return findByStringParam(login, FIND_BY_LOGIN);
    }

    @Override
    public User findUser(String login, String password) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(FIND_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                User user = new User();
                UserType userType = new UserType();
                user.setId(resultSet.getLong("user.id"));
                user.setLogin(login);
                user.setIdUserType(resultSet.getLong("id_user_type"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                userType.setId(resultSet.getLong("id_user_type"));
                userType.setType(resultSet.getString("type"));
                userType.setDescription(resultSet.getString("description"));
                user.setUserType(userType);
                return user;
            }
        } catch (SQLException e) {
            LOGGER.error("Not find user in db", e);
            throw new DatabaseRuntimeException("Not find user in db", e);
        }
        return null;
    }
}
