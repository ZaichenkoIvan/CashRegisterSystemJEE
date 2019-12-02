package dao.daoimpl;

import dao.AbstractGenericDao;
import dao.PoolConnection;
import dao.UserTypeDao;
import entity.UserTypeEntity;
import exception.DatabaseRuntimeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserTypeDaoImpl extends AbstractGenericDao<UserTypeEntity> implements UserTypeDao {
    private static final String INSERT = "INSERT INTO user_type " +
            "(type, description) values(?, ?)";
    private static final String UPDATE = "UPDATE user_type SET type=?, description=? WHERE id=?";
    private static final String FIND_BY_TYPE = "SELECT * FROM cashreg.user_type WHERE type = ?";

    public UserTypeDaoImpl(PoolConnection poolConnection) {
        super(poolConnection);
    }

    @Override
    protected void setInsertProperties(PreparedStatement statement, UserTypeEntity usertype) {
        try {
            statement.setString(1, usertype.getType());
            statement.setString(2, usertype.getDescription());
        } catch (SQLException e) {
            LOGGER.error("Not insert user type in db", e);
            throw new DatabaseRuntimeException("Not insert user type in db", e);
        }
    }

    @Override
    protected void setUpdateProperties(PreparedStatement statement, UserTypeEntity usertype) {
        try {
            statement.setString(1, usertype.getType());
            statement.setString(2, usertype.getDescription());
            statement.setLong(3, usertype.getId());
        } catch (SQLException e) {
            LOGGER.error("Not update user type in db", e);
            throw new DatabaseRuntimeException("Not update user type in db", e);
        }
    }

    @Override
    protected UserTypeEntity parseToOne(ResultSet resultSet) {
        UserTypeEntity userType = new UserTypeEntity();
        try {
            userType.setId(resultSet.getLong("id"));
            userType.setType(resultSet.getString("type"));
            userType.setDescription(resultSet.getString("description"));
            return userType;
        } catch (SQLException e) {
            LOGGER.error("Not find user type in db", e);
            throw new DatabaseRuntimeException("Not find user type in db", e);
        }
    }

    @Override
    public Optional<UserTypeEntity> findById(Long id) {
        throw new UnsupportedOperationException("Not implementation yet");
    }

    @Override
    public Long insert(UserTypeEntity usertype) {
        return insert(usertype, INSERT);
    }

    @Override
    public void update(UserTypeEntity usertype) {
        update(usertype, UPDATE);
    }

    @Override
    public Long findUserType(String type) {
        return findByStringParam(type, FIND_BY_TYPE).getId();
    }
}
