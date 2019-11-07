package project.model.dao.impl;

import project.model.dao.AbstractDao;
import project.model.dao.UserDao;
import project.model.dao.connector.PoolConnector;
import project.model.entity.UserEntity;
import project.model.entity.enums.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<UserEntity> implements UserDao {
    private static final String INSERT_USER = "INSERT INTO project.users(user_name, user_surname, user_email, user_password, user_role) VALUES(?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM project.users WHERE user_id = ?";
    private static final String FIND_ALL_USERS = "SELECT * FROM project.users LIMIT ?, ?";
    private static final String COUNT = "SELECT * FROM project.users";
    private static final String FIND_BY_EMAIL = "SELECT * FROM project.users WHERE user_email = ?";
    private static final String UPDATE_USER = "UPDATE project.users SET user_name = ?, user_surname = ?, user_email = ?, user_password = ?, user_role = ? WHERE user_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM project.users WHERE user_id = ?";

    public UserDaoImpl(PoolConnector connector) {
        super(connector);
    }

    @Override
    public boolean save(UserEntity user) {
        return save(user, INSERT_USER);
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return findById(id, FIND_BY_ID);
    }

    @Override
    public List<UserEntity> findAll(int currentPage, int recordsPerPage) {
        return findAll(FIND_ALL_USERS, currentPage, recordsPerPage);
    }

    @Override
    public int getNumberOfRows() {
        return getNumberOfRows(COUNT);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return findOneByStringParam(email, FIND_BY_EMAIL);
    }

    @Override
    public void update(UserEntity user) {
        update(user, UPDATE_USER);
    }

    @Override
    public boolean deleteById(Integer id) {
        return deleteById(id, DELETE_BY_ID);
    }

    @Override
    protected Optional<UserEntity> mapResultSetToEntity(ResultSet user) throws SQLException {

        return Optional.ofNullable(UserEntity.builder()
                .withId(user.getInt(1))
                .withName(user.getString(2))
                .withSurname(user.getString(3))
                .withEmail(user.getString(4))
                .withPassword(user.getString(5))
                .withRole(Role.valueOf(user.getString(6)))
                .build());
    }

    @Override
    protected void updateStatementMapper(UserEntity user, PreparedStatement preparedStatement) throws SQLException {
        createStatementMapper(user, preparedStatement);
        preparedStatement.setInt(6, user.getId());
    }

    @Override
    protected void createStatementMapper(UserEntity user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getRole().toString());
    }
}
