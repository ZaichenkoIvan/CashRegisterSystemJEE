package main.java.dao.daoimpl;

import main.java.dao.AbstractGenericDao;
import main.java.dao.GoodsDao;
import main.java.dao.PoolConnection;
import main.java.entity.Goods;
import main.java.exception.DatabaseRuntimeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GoodsDaoImpl extends AbstractGenericDao<Goods> implements GoodsDao {
    private static final String INSERT = "INSERT INTO goods " +
            "(code, name, quant, price, measure, comments) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE goods SET code=?, name=?, quant=?, price=?, measure=?, comments=? WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM goods ORDER BY code LIMIT ?,?";
    private static final String FIND_BY_ID = "SELECT * FROM goods WHERE id = ?";
    private static final String FIND_BY_CODE = "SELECT * FROM goods WHERE code = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM goods WHERE lower(name) = ?";
    private static final String COUNT = "SELECT COUNT(*) FROM cashreg.goods";


    public GoodsDaoImpl(PoolConnection poolConnection) {
        super(poolConnection);
    }

    @Override
    protected void setInsertProperties(PreparedStatement statement, Goods goods) {
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
    protected void setUpdateProperties(PreparedStatement statement, Goods goods) {
        setInsertProperties(statement, goods);
        try {
            statement.setLong(7, goods.getId());
        } catch (SQLException e) {
            LOGGER.error("Not update good in db", e);
            throw new DatabaseRuntimeException("Not update good in db", e);
        }
    }

    @Override
    protected Goods parseToOne(ResultSet resultSet) {
        Goods goods = new Goods();
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
    public Long insert(Goods goods) {
        return insert(goods, INSERT);
    }

    public List<Goods> findAll(Integer page, Integer recordsPerPage) {
        return findPaginatedList(page, recordsPerPage, FIND_ALL);
    }

    @Override
    public long count() {
        return count(COUNT);
    }

    @Override
    public void update(Goods goods) {
        update(goods, UPDATE);
    }


    @Override
    public Optional<Goods> findById(Long id) {
        return Optional.ofNullable(findByLongParam(id, FIND_BY_ID));
    }

    @Override
    public Goods findGoods(int code) {
        return findByIntParam(code, FIND_BY_CODE);
    }

    @Override
    public Goods findGoods(String name) {
        return findByStringParam(name, FIND_BY_NAME);
    }
}
