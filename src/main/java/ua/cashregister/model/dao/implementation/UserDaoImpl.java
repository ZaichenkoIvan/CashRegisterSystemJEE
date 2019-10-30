package ua.cashregister.model.dao.implementation;

import ua.cashregister.model.dao.GenericAbstractDao;
import ua.cashregister.model.dao.MapperFromDB;
import ua.cashregister.model.dao.MapperToDB;
import ua.cashregister.model.dao.UserDao;
import ua.cashregister.model.dao.exception.DataNotFoundRuntimeException;
import ua.cashregister.model.domain.User;
import ua.cashregister.model.domain.enums.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends GenericAbstractDao<User> implements UserDao {
    private static String SQL_select_base = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "ORDER BY user_id ";
    private static String SQL_selectAll = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "ORDER BY user_id;";
    private static String SQL_selectById = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_id=?;";
    private static String SQL_selectByName = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_name=?;";
    private static String SQL_selectByRole = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_roles.role_description=?;";
    private static String SQL_addNew = "INSERT INTO project.users " +
            "(user_name, user_password, user_phone, user_address, role_id) " +
            "VALUES (?,?,?,?,?)";
    private static String SQL_updateById = "UPDATE project.users SET " +
            "user_name=?, user_password=?, user_phone=?, user_address=?, role_id=?" +
            "WHERE user_id=?;";
    private static String SQL_deleteUser = "DELETE FROM project.users WHERE user_id=?;";

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
        return findAll(SQL_selectAll);
    }

    @Override
    public List<User> findUsers() {
        return findAllFromTo(SQL_select_base);
    }

    @Override
    public List<User> findUserByRole(UserRole role) {
        return findAsListBy(SQL_selectByRole, role.toString());
    }

    @Override
    public User findById(Integer id) {
        return findBy(SQL_selectById, id);
    }

    @Override
    public User findUserByName(String name) {
        return findBy(SQL_selectByName, name);
    }

    @Override
    public boolean save(User user) {
        return addToDB(user, SQL_addNew);
    }

    @Override
    public boolean update(User user) {
        return updateInDB(user, SQL_updateById, 8, user.getId());
    }

    @Override
    public boolean deleteById(User user) {
        return deleteFromDB(SQL_deleteUser, user.getId());
    }
}
