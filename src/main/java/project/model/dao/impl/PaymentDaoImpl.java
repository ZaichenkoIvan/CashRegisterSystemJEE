package project.model.dao.impl;

import project.model.dao.PaymentDao;
import project.model.entity.InvoiceEntity;
import project.model.entity.PaymentEntity;
import project.model.entity.UserEntity;
import project.model.dao.AbstractDao;
import project.model.dao.connector.PoolConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PaymentDaoImpl extends AbstractDao<PaymentEntity> implements PaymentDao {

    private static final String INSERT_PAYMENT = "INSERT INTO project.payments(payment_value, invoice_id, user_id) VALUES(?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM project.payments WHERE Payment_id = ?";
    private static final String FIND_BY_USER = "SELECT * FROM project.payments WHERE user_id = ?";
    private static final String FIND_ALL_PAYMENT = "SELECT * FROM project.payments LIMIT ?, ?";
    private static final String COUNT = "SELECT * FROM project.payments";
    private static final String UPDATE_PAYMENT = "UPDATE project.payments SET payment_value = ?, invoice_id = ?, user_id = ? WHERE Payment_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM project.payments WHERE Payment_id = ?";

    public PaymentDaoImpl(PoolConnector connector) {
        super(connector);
    }

    @Override
    public boolean save(PaymentEntity Payment) {
        return save(Payment, INSERT_PAYMENT);
    }

    @Override
    public List<PaymentEntity> findByUser(Integer id) {
        return findEntitiesByForeignKey(id, FIND_BY_USER);
    }

    @Override
    public Optional<PaymentEntity> findById(Integer id) {
        return findById(id, FIND_BY_ID);
    }

    @Override
    public List<PaymentEntity> findAll(int currentPage, int recordsPerPage) {
        return findAll(FIND_ALL_PAYMENT, currentPage, recordsPerPage);
    }

    @Override
    public void update(PaymentEntity Payment) {
        update(Payment, UPDATE_PAYMENT);
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
    protected Optional<PaymentEntity> mapResultSetToEntity(ResultSet payment) throws SQLException {
        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                .withId(payment.getInt(3))
                .build();
        UserEntity user = UserEntity.builder()
                .withId(payment.getInt(4))
                .build();
        return Optional.of(new PaymentEntity(payment.getInt(1),payment.getInt(2), invoiceEntity, user));
    }

    @Override
    protected void updateStatementMapper(PaymentEntity Payment, PreparedStatement preparedStatement) throws SQLException {
        createStatementMapper(Payment, preparedStatement);
        preparedStatement.setInt(4, Payment.getId());
    }

    @Override
    protected void createStatementMapper(PaymentEntity Payment, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, Payment.getPaymentValue());
        preparedStatement.setInt(2, Payment.getInvoiceEntity().getId());
        preparedStatement.setInt(3, Payment.getUser().getId());
    }
}
