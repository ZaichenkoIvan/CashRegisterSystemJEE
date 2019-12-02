package dao.daoimpl;

import dao.AbstractGenericDao;
import dao.FiscalDao;
import dao.PoolConnection;
import entity.FiscalEntity;
import exception.DatabaseRuntimeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class FiscalDaoImpl extends AbstractGenericDao<FiscalEntity> implements FiscalDao {
    private static final String INSERT = "INSERT INTO fiscal (total) values(?)";
    private static final String UPDATE = "UPDATE cashreg.fiscal SET fiscal=? WHERE id=?";

    public FiscalDaoImpl(PoolConnection poolConnection) {
        super(poolConnection);
    }

    @Override
    protected void setInsertProperties(PreparedStatement statement, FiscalEntity fiscal) {
        try {
            statement.setDouble(1, fiscal.getTotal());
        } catch (SQLException e) {
            LOGGER.error("Not insert fiscal in db", e);
            throw new DatabaseRuntimeException("Not insert fiscal in db", e);
        }
    }

    @Override
    protected void setUpdateProperties(PreparedStatement statement, FiscalEntity fiscal) {
        try {
            statement.setDouble(1, fiscal.getTotal());
            statement.setLong(2, fiscal.getId());
        } catch (SQLException e) {
            LOGGER.error("Not update fiscal in db", e);
            throw new DatabaseRuntimeException("Not update fiscal in db", e);
        }
    }

    @Override
    protected FiscalEntity parseToOne(ResultSet resultSet) {
        throw new UnsupportedOperationException("Unsupported operation yet");
    }

    @Override
    public Optional<FiscalEntity> findById(Long id) {
        throw new UnsupportedOperationException("Not implementation yet");
    }

    @Override
    public Long insert(FiscalEntity fiscal) {
        return insert(fiscal, INSERT);
    }

    @Override
    public void update(FiscalEntity fiscal) {
        update(fiscal, UPDATE);
    }
}
