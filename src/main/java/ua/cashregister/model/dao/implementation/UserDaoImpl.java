package ua.cashregister.model.dao.implementation;

import ua.cashregister.model.dao.GenericAbstractDao;
import ua.cashregister.model.dao.mapper.MapperFromDB;
import ua.cashregister.model.dao.mapper.MapperToDB;
import ua.cashregister.model.dao.UserDao;
import ua.cashregister.model.dao.exception.DataNotFoundRuntimeException;
import ua.cashregister.model.domain.User;
import ua.cashregister.model.domain.enums.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends GenericAbstractDao<User> implements UserDao {
    private static final String SELECT_BASE = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "ORDER BY user_id ";
    private static final String SELECT_ALL_USER = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "ORDER BY user_id;";
    private static final String SELECT_BY_ID_USER = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_id=?;";
    private static final String SELECT_BY_NAME_USER = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_name=?;";
    private static final String SELECT_BY_ROLE_USER = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_roles.role_description=?;";
    private static final String INSERT_USER = "INSERT INTO project.users " +
            "(user_name, user_password, user_phone, user_address, role_id) " +
            "VALUES (?,?,?,?,?)";
    private static final String UPDATE_BY_ID_USER = "UPDATE project.users SET " +
            "user_name=?, user_password=?, user_phone=?, user_address=?, role_id=?" +
            "WHERE user_id=?;";
    private static final String DELETE_USER = "DELETE FROM project.users WHERE user_id=?;";

    private MapperToDB<User, PreparedStatement> mapperToDB = (User user, PreparedStatement preparedStatement) -> {
        try {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getUserRole().ordinal());
        } catch (SQLException e) {
            throw new DataNotFoundRuntimeException();
        }
    };

    private MapperFromDB<ResultSet, User> mapperFromDB = (ResultSet resultSet) -> {
        try {
            return User.builder()
            .withId(resultSet.getInt("user_id"))
            .withName(resultSet.getString("user_name"))
            .withPassword(resultSet.getString("user_password"))
            .withPhoneNumber(resultSet.getString("user_phone"))
            .withEmail(resultSet.getString("user_email"))
            .withUserRole(UserRole.valueOf(resultSet.getString("role_description")))
                    .build();
        } catch (SQLException e) {
            throw new DataNotFoundRuntimeException();
        }
    };

    public UserDaoImpl() {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }


    @Override
    public List<User> findAllUsersInDB() {
        return findAll(SELECT_ALL_USER);
    }

    @Override
    public List<User> findUsers() {
        return findAllFromTo(SELECT_BASE);
    }

    @Override
    public List<User> findUserByRole(UserRole role) {
        return findAsListBy(SELECT_BY_ROLE_USER, role.toString());
    }

    @Override
    public User findById(Integer id) {
        return findBy(SELECT_BY_ID_USER, id);
    }

    @Override
    public User findUserByName(String name) {
        return findBy(SELECT_BY_NAME_USER, name);
    }

    @Override
    public boolean save(User user) {
        return addToDB(user, INSERT_USER);
    }

    @Override
    public boolean update(User user) {
        return updateInDB(user, UPDATE_BY_ID_USER, 8, user.getId());
    }

    @Override
    public boolean deleteById(User user) {
        return deleteFromDB(DELETE_USER, user.getId());
    }
}
