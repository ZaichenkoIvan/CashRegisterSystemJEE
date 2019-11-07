package project.model.dao.impl;

import project.model.dao.ProductDao;
import project.model.entity.ProductEntity;
import project.model.dao.AbstractDao;
import project.model.dao.connector.PoolConnector;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl extends AbstractDao<ProductEntity> implements ProductDao {
    private static final String INSERT_PRODUCT = "INSERT INTO project.products(product_code, product_name, product_description, product_description, product_cost, product_quantity) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM project.products WHERE product_id = ?";
    private static final String FIND_ALL_PRODUCTS = "SELECT * FROM project.products LIMIT ?, ?";
    private static final String COUNT = "SELECT * FROM project.products";
    private static final String UPDATE_PRODUCT = "UPDATE project.products SET product_code = ?, product_name = ?, product_description = ?, product_description = ?, product_cost = ?, product_quantity = ?  WHERE product_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM project.products WHERE product_id = ?";

    public ProductDaoImpl(PoolConnector connector) {
        super(connector);
    }

    @Override
    public boolean save(ProductEntity entity) {
        return save(entity, INSERT_PRODUCT);
    }

    @Override
    public Optional<ProductEntity> findById(Integer id) {
        return findById(id, FIND_BY_ID);
    }

    @Override
    public List<ProductEntity> findAll(int currentPage, int recordsPerPage) {
        return findAll(FIND_ALL_PRODUCTS, currentPage, recordsPerPage);
    }

    @Override
    public void update(ProductEntity entity) {
        update(entity, UPDATE_PRODUCT);
    }

    @Override
    public boolean deleteById(Integer id) {
        return deleteById(id, DELETE_BY_ID);
    }

    @Override
    public int getNumberOfRows() {
        return getNumberOfRows(COUNT);
    }

    @Override
    protected void updateStatementMapper(ProductEntity productEntity, PreparedStatement preparedStatement) throws SQLException {
        createStatementMapper(productEntity, preparedStatement);
        preparedStatement.setInt(5, productEntity.getId());
    }

    @Override
    protected void createStatementMapper(ProductEntity productEntity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, productEntity.getCode());
        preparedStatement.setString(2, productEntity.getName());
        preparedStatement.setString(3, productEntity.getDescription());
        preparedStatement.setInt(4, productEntity.getCost());
        preparedStatement.setInt(5, productEntity.getQuantity());
    }

    @Override
    protected Optional<ProductEntity> mapResultSetToEntity(ResultSet product) throws SQLException {
        return Optional.of(ProductEntity.builder().withId(product.getInt(1))
                .withCode(product.getString(2))
                .withName(product.getString(3))
                .withDescription(product.getString(4))
                .withCost(product.getInt(5))
                .withQuantity(product.getInt(6))
                .build());
    }
}
