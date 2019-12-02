package main.java.dao.daoimpl;

import main.java.dao.AbstractGenericDao;
import main.java.dao.CheckDao;
import main.java.dao.PoolConnection;
import main.java.entity.CheckEntity;
import main.java.exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CheckDaoImpl extends AbstractGenericDao<CheckEntity> implements CheckDao {
    private static final String INSERT = "INSERT INTO cashreg.check (creator, total, discount, canceled) "
            + "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE cashreg.check SET creator = ?, total = ?, discount = ?, canceled = ?," +
            " registration = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM cashreg.check WHERE id = ?";
    private static final String UPDATE_REGISTRATION = "UPDATE cashreg.check SET registration = ? WHERE canceled = 0 " +
            "AND cast(crtime as date) = current_date() AND registration IS NULL";

    public CheckDaoImpl(PoolConnection poolConnection) {
        super(poolConnection);
    }

    @Override
    protected void setInsertProperties(PreparedStatement statement, CheckEntity check) {
        try {
            statement.setLong(1, check.getCreator());
            statement.setDouble(2, check.getTotal());
            statement.setDouble(3, check.getDiscount());
            statement.setInt(4, check.getCanceled());
        } catch (SQLException e) {
            LOGGER.error("Not insert check in db", e);
            throw new DatabaseRuntimeException("Not insert check in db", e);
        }
    }

    @Override
    protected void setUpdateProperties(PreparedStatement statement, CheckEntity check) {
        setInsertProperties(statement, check);
        try {
            statement.setObject(5, check.getRegistration());
            statement.setLong(6, check.getId());
        } catch (SQLException e) {
            LOGGER.error("Not update check in db", e);
            throw new DatabaseRuntimeException("Not update check in db", e);
        }
    }

    @Override
    protected CheckEntity parseToOne(ResultSet resultSet) {
        CheckEntity check = new CheckEntity();
        try {
            check.setId(resultSet.getLong("id"));
            check.setCrtime(resultSet.getDate("crtime"));
            check.setCreator(resultSet.getLong("creator"));
            check.setTotal(resultSet.getDouble("total"));
            check.setDiscount(resultSet.getDouble("discount"));
            check.setCanceled(resultSet.getInt("canceled"));
            check.setRegistration((Integer) resultSet.getObject("registration"));
        } catch (SQLException e) {
            LOGGER.error("Not find check in db", e);
            throw new DatabaseRuntimeException("Not find check in db", e);
        }
        return check;
    }

    @Override
    public Long insert(CheckEntity check) {
        return insert(check, INSERT);
    }

    @Override
    public void update(CheckEntity check) {
        update(check, UPDATE_BY_ID);
    }

    @Override
    public Optional<CheckEntity> findById(Long id) {
        return Optional.ofNullable(findByLongParam(id, SELECT_BY_ID));
    }

    @Override
    public int update(Integer value) {
        int rows = 0;
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_REGISTRATION)) {
            statement.setInt(1, value);
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Check update() error", e);
        }
        return rows;
    }
}
