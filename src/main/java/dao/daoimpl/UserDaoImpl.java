package dao.daoimpl;

import dao.AbstractGenericDao;
import dao.PoolConnection;
import dao.UserDao;
import entity.UserEntity;
import entity.UserTypeEntity;
import exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractGenericDao<UserEntity> implements UserDao {
    private static final String INSERT = "INSERT INTO user" +
            "(login, password, name, id_user_type) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE user SET login=?, password=?, name=?, id_user_type=? WHERE id=?";
    private static final String FIND_BY_LOGIN = "SELECT * FROM user " +
            "INNER JOIN user_type ON user_type.id = user.id_user_type " +
            "WHERE user.login = ?";

    public UserDaoImpl(PoolConnection poolConnection) {
        super(poolConnection);
    }

    @Override
    protected void setInsertProperties(PreparedStatement statement, UserEntity user) {
        try {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setLong(4, user.getIdUserType());
        } catch (SQLException e) {
            LOGGER.warn("Not insert user in db", e);
            throw new DatabaseRuntimeException("Not insert user in db", e);
        }
    }

    @Override
    protected void setUpdateProperties(PreparedStatement statement, UserEntity user) {
        setInsertProperties(statement, user);
        try {
            statement.setLong(5, user.getId());
        } catch (SQLException e) {
            LOGGER.warn("Not update user in db", e);
            throw new DatabaseRuntimeException("Not update user in db", e);
        }
    }

    @Override
    protected UserEntity parseToOne(ResultSet resultSet) {
        UserEntity user = new UserEntity();
        try {
            user.setId(resultSet.getLong("id"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setIdUserType(resultSet.getLong("id_user_type"));
            user.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.warn("Not find user in db", e);
            throw new DatabaseRuntimeException("Not find user in db", e);
        }

        return user;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        throw new UnsupportedOperationException("Not implementation yet");
    }

    @Override
    public Long insert(UserEntity user) {
        return insert(user, INSERT);
    }

    @Override
    public void update(UserEntity user) {
        update(user, UPDATE);
    }

    @Override
    public Optional<UserEntity> findUserByLogin(String login) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(FIND_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                UserEntity user = new UserEntity();
                UserTypeEntity userType = new UserTypeEntity();
                user.setId(resultSet.getLong("user.id"));
                user.setLogin(login);
                user.setIdUserType(resultSet.getLong("id_user_type"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                userType.setId(resultSet.getLong("id_user_type"));
                userType.setType(resultSet.getString("type"));
                userType.setDescription(resultSet.getString("description"));
                user.setUserType(userType);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            LOGGER.warn("Not find user in db", e);
            throw new DatabaseRuntimeException("Not find user in db", e);
        }
        return null;
    }
}
