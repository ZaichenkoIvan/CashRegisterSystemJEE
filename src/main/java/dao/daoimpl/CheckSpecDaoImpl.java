package main.java.dao.daoimpl;

import main.java.dao.AbstractGenericDao;
import main.java.dao.CheckSpecDao;
import main.java.dao.PoolConnection;
import main.java.entity.Checkspec;
import main.java.exception.DatabaseRuntimeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CheckSpecDaoImpl extends AbstractGenericDao<Checkspec> implements CheckSpecDao {
    private static final String INSERT = "INSERT INTO checkspec" +
            "(id_check, id_good, quant, price, total, nds, ndstotal, canceled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_BY_CHECK_ID = "SELECT * FROM vcheckspec  WHERE id_check = ? ORDER BY id";
    private static final String UPDATE_BY_ID = "UPDATE checkspec SET id_check = ?, id_good = ?, quant = ?, price = ?," +
            " total = ?, nds = ?, ndstotal = ?, canceled = ? WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM checkspec WHERE id = ?";


    public CheckSpecDaoImpl(PoolConnection poolConnection) {
        super(poolConnection);
    }

    @Override
    protected void setInsertProperties(PreparedStatement statement, Checkspec checkspec) {
        try {
            statement.setLong(1, checkspec.getIdCheck());
            statement.setLong(2, checkspec.getIdGood());
            statement.setDouble(3, checkspec.getQuant());
            statement.setDouble(4, checkspec.getPrice());
            statement.setDouble(5, checkspec.getTotal());
            statement.setDouble(6, checkspec.getNds());
            statement.setDouble(7, checkspec.getNdstotal());
            statement.setInt(8, checkspec.getCanceled());
        } catch (SQLException e) {
            LOGGER.error("Not insert checkspec in db", e);
            throw new DatabaseRuntimeException("Not insert checkspec in db", e);
        }
    }

    @Override
    protected void setUpdateProperties(PreparedStatement statement, Checkspec checkspec) {
        setInsertProperties(statement, checkspec);
        try {
            statement.setLong(9, checkspec.getId());
        } catch (SQLException e) {
            LOGGER.error("Not update checkspec in db", e);
            throw new DatabaseRuntimeException("Not update checkspec in db", e);
        }
    }

    @Override
    protected Checkspec parseToOne(ResultSet resultSet) {
        Checkspec checkspec = new Checkspec();
        try {
            checkspec.setId(resultSet.getLong("id"));
            checkspec.setIdCheck(resultSet.getLong("id_check"));
            checkspec.setIdGood(resultSet.getLong("id_good"));
            checkspec.setQuant(resultSet.getDouble("quant"));
            checkspec.setPrice(resultSet.getDouble("price"));
            checkspec.setTotal(resultSet.getDouble("total"));
            checkspec.setNds(resultSet.getInt("nds"));
            checkspec.setNdstotal(resultSet.getDouble("ndstotal"));
            checkspec.setCanceled(resultSet.getInt("canceled"));
            checkspec.setXcode(resultSet.getInt("xcode"));
            checkspec.setXname(resultSet.getString("xname"));
        } catch (SQLException e) {
            LOGGER.error("Not find checkspec in db", e);
            throw new DatabaseRuntimeException("Not find checkspec in db", e);
        }

        return checkspec;
    }

    @Override
    public Long insert(Checkspec checkspec) {
        return insert(checkspec, INSERT);
    }

    @Override
    public void insertAll(List<Checkspec> specifications) {
        for (Checkspec checkspec : specifications
        ) {
            insert(checkspec);
        }
    }

    @Override
    public List<Checkspec> findAllByCheckId(Long idCheck) {
        return findListByLongParam(idCheck, FIND_ALL_BY_CHECK_ID);
    }

    @Override
    public void update(Checkspec checkspec) {
        update(checkspec, UPDATE_BY_ID);
    }

    @Override
    public Optional<Checkspec> findById(Long id) {
        return Optional.ofNullable(findByLongParam(id, FIND_BY_ID));
    }
}
