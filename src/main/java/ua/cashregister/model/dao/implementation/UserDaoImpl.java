package ua.cashregister.model.dao.implementation;

import ua.cashregister.model.dao.GenericAbstractDao;
import ua.cashregister.model.dao.Mapper;
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
            "(user_name, user_password, user_phone, user_email, user_address, role_id, user_notes) " +
            "VALUES (?,?,?,?,?,?,?)";
    private static String SQL_updateByName = "UPDATE project.users SET " +
            "user_name=?, user_password=?, user_phone=?, user_email=?, user_address=?, role_id=?, user_notes=? " +
            "WHERE user_name=?;";
    private static String SQL_updateById = "UPDATE project.users SET " +
            "user_name=?, user_password=?, user_phone=?, user_email=?, user_address=?, role_id=?, user_notes=? " +
            "WHERE user_id=?;";
    private static String SQL_deleteUser = "DELETE FROM project.users WHERE user_name=?;";

    private Mapper<User, PreparedStatement> mapperToDB = (User user, PreparedStatement preparedStatement) -> {
        try {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setInt(6, user.getUserRole().ordinal());
            preparedStatement.setString(7, user.getNotes());
        } catch (SQLException e) {
            throw new DataNotFoundRuntimeException();
        }
        return null;
    };

    private Mapper<ResultSet, User> mapperFromDB = (ResultSet resultSet, User user) -> {
        try {
            user.setId(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setPassword(resultSet.getString("user_password"));
            user.setPhoneNumber(resultSet.getString("user_phone"));
            user.setEmail(resultSet.getString("user_email"));
            user.setAddress(resultSet.getString("user_address"));
            user.setUserRole(UserRole.valueOf(resultSet.getString("role_description")));
            user.setNotes(resultSet.getString("user_notes"));
        } catch (SQLException e) {
            throw new DataNotFoundRuntimeException();
        }
        return null;
    };

    public UserDaoImpl() {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }


    @Override
    public List<User> findAllUsersInDB() {
        return findAll(User.class, SQL_selectAll);
    }

    @Override
    public List<User> findUsers() {
        return findAllFromTo(User.class, SQL_select_base);
    }

    @Override
    public List<User> findUserByRole(UserRole role) {
        return findAsListBy(User.class, SQL_selectByRole, role.toString());
    }

    @Override
    public User findById(Integer id) {
        return findBy(User.class, SQL_selectById, id);
    }

    @Override
    public User findUserByName(String name) {
        return findBy(User.class, SQL_selectByName, name);
    }

    @Override
    public boolean addToDB(User user) {
        return addToDB(user, SQL_addNew);
    }

    @Override
    public boolean updateInDB(User user) {
        return updateInDB(user, SQL_updateById, 8, user.getId());
    }

    @Override
    public boolean deleteFromDB(User user) {
        return deleteFromDB(SQL_deleteUser, user.getName());
    }
}
