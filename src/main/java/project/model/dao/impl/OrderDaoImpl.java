package project.model.dao.impl;

import project.model.dao.OrderDao;
import project.model.entity.InvoiceEntity;
import project.model.entity.OrderEntity;
import project.model.entity.ProductEntity;
import project.model.dao.AbstractDao;
import project.model.dao.connector.PoolConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl extends AbstractDao<OrderEntity> implements OrderDao {
    private static final String INSERT_ORDER = "INSERT INTO project.orders(order_number, product_id, invoice_id) VALUES(?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM project.orders WHERE order_id = ?";
    private static final String FIND_BY_INVOICE_ID = "SELECT * FROM project.orders WHERE invoice_id = ?";
    private static final String FIND_ALL_ORDER = "SELECT * FROM project.orders";
    private static final String UPDATE_ORDER = "UPDATE project.orders SET number = ?, product_id = ?, invoice_id = ? WHERE order_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM project.orders WHERE order_id = ?";

    public OrderDaoImpl(PoolConnector connector) {
        super(connector);
    }

    @Override
    public boolean save(OrderEntity order) {
        return save(order, INSERT_ORDER);
    }

    @Override
    public Optional<OrderEntity> findById(Integer id) {
        return findById(id, FIND_BY_ID);
    }

    @Override
    public List<OrderEntity> findAll() {
        return findAll(FIND_ALL_ORDER);
    }

    @Override
    public void update(OrderEntity orderEntity) {
        update(orderEntity, UPDATE_ORDER);
    }

    @Override
    public boolean deleteById(Integer id) {
        return deleteById(id, DELETE_BY_ID);
    }

    @Override
    protected void updateStatementMapper(OrderEntity orderEntity , PreparedStatement preparedStatement) throws SQLException {
        createStatementMapper(orderEntity, preparedStatement);
        preparedStatement.setInt(4, orderEntity.getId());
    }

    @Override
    protected void createStatementMapper(OrderEntity orderEntity , PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, orderEntity.getNumber());
        preparedStatement.setInt(2, orderEntity.getProductEntity().getId());
        preparedStatement.setInt(3, orderEntity.getInvoiceEntity().getId());
    }

    @Override
    protected Optional<OrderEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        ProductEntity productEntity = ProductEntity.builder()
                .withId(resultSet.getInt(3))
                .build();

        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                .withId(resultSet.getInt(4))
                .build();

        return Optional.of(OrderEntity.builder().withId(resultSet.getInt(1))
                .withNumber(resultSet.getInt(2))
                .withProductEntity(productEntity)
                .withInvoiceEntity(invoiceEntity)
                .build());
    }

    @Override
    public List<OrderEntity> findByInvoiceId(Integer id) {
        return findEntitiesByForeignKey(id, FIND_BY_INVOICE_ID);
    }
}