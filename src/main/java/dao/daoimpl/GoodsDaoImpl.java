package main.java.dao.daoimpl;

import main.java.dao.AbstractGenericDao;
import main.java.dao.GoodsDao;
import main.java.dao.PoolConnection;
import main.java.entity.GoodsEntity;
import main.java.exception.DatabaseRuntimeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GoodsDaoImpl extends AbstractGenericDao<GoodsEntity> implements GoodsDao {
    private static final String INSERT = "INSERT INTO goods " +
            "(code, name, quant, price, measure, comments) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE goods SET code=?, name=?, quant=?, price=?, measure=?, comments=? WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM goods ORDER BY code LIMIT ?,?";
    private static final String FIND_BY_ID = "SELECT * FROM goods WHERE id = ?";
    private static final String FIND_BY_CODE = "SELECT * FROM goods WHERE code = ?";
    private static final String COUNT = "SELECT COUNT(*) FROM cashreg.goods";


    public GoodsDaoImpl(PoolConnection poolConnection) {
        super(poolConnection);
    }

    @Override
    protected void setInsertProperties(PreparedStatement statement, GoodsEntity goods) {
        try {
            statement.setInt(1, goods.getCode());
            statement.setString(2, goods.getName());
            statement.setDouble(3, goods.getQuant());
            statement.setDouble(4, goods.getPrice());
            statement.setString(5, goods.getMeasure());
            statement.setString(6, goods.getComments());
        } catch (SQLException e) {
            LOGGER.error("Not insert good in db", e);
            throw new DatabaseRuntimeException("Not insert good in db", e);
        }

    }

    @Override
    protected void setUpdateProperties(PreparedStatement statement, GoodsEntity goods) {
        setInsertProperties(statement, goods);
        try {
            statement.setLong(7, goods.getId());
        } catch (SQLException e) {
            LOGGER.error("Not update good in db", e);
            throw new DatabaseRuntimeException("Not update good in db", e);
        }
    }

    @Override
    protected GoodsEntity parseToOne(ResultSet resultSet) {
        GoodsEntity goods = new GoodsEntity();
        try {
            goods.setId(resultSet.getLong("id"));
            goods.setCode(resultSet.getInt("code"));
            goods.setName(resultSet.getString("name"));
            goods.setQuant(resultSet.getDouble("quant"));
            goods.setPrice(resultSet.getDouble("price"));
            goods.setMeasure(resultSet.getString("measure"));
            goods.setComments(resultSet.getString("comments"));
        } catch (SQLException e) {
            LOGGER.error("Not find good in db", e);
            throw new DatabaseRuntimeException("Not find good in db", e);
        }
        return goods;
    }

    @Override
    public Long insert(GoodsEntity goods) {
        return insert(goods, INSERT);
    }

    public List<GoodsEntity> findAll(int page, int recordsPerPage) {
        return findPaginatedList((page - 1) * recordsPerPage, recordsPerPage, FIND_ALL);
    }

    @Override
    public long count() {
        return count(COUNT);
    }

    @Override
    public void update(GoodsEntity goods) {
        update(goods, UPDATE);
    }


    @Override
    public Optional<GoodsEntity> findById(Long id) {
        return Optional.ofNullable(findByLongParam(id, FIND_BY_ID));
    }

    @Override
    public Optional<GoodsEntity> findGoods(int code) {
        return Optional.ofNullable(findByIntParam(code, FIND_BY_CODE));
    }
}
