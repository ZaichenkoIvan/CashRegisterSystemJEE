package project.model.dao.impl;

import project.model.dao.InvoiceDao;
import project.model.entity.InvoiceEntity;
import project.model.entity.enums.Status;
import project.model.dao.AbstractDao;
import project.model.dao.connector.PoolConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InvoiceDaoImpl extends AbstractDao<InvoiceEntity> implements InvoiceDao {
    private static final String INSERT_INVOICE = "INSERT INTO project.invoices(invoice_cost, invoice_isPaid, user_id, invoice_status) VALUES(?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM project.invoices WHERE invoice_id = ?";
    private static final String FIND_ALL_INVOICES = "SELECT * FROM project.invoices";
    private static final String UPDATE_INVOICE = "UPDATE project.invoices SET invoice_cost = ?, invoice_isPaid = ?, user_id =?, invoice_status =? WHERE invoice_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM project.invoices WHERE invoice_id = ?";

    public InvoiceDaoImpl(PoolConnector connector) {
        super(connector);
    }

    @Override
    public boolean save(InvoiceEntity Invoice) {
        return save(Invoice, INSERT_INVOICE);
    }

    @Override
    public Optional<InvoiceEntity> findById(Integer id) {
        return findById(id, FIND_BY_ID);
    }

    @Override
    public List<InvoiceEntity> findAll() {
        return findAll(FIND_ALL_INVOICES);
    }

    @Override
    public void update(InvoiceEntity Invoice) {
        update(Invoice, UPDATE_INVOICE);
    }

    @Override
    public boolean deleteById(Integer id) {
        return deleteById(id, DELETE_BY_ID);
    }

    @Override
    protected void updateStatementMapper(InvoiceEntity Invoice, PreparedStatement preparedStatement) throws SQLException {
        createStatementMapper(Invoice, preparedStatement);
        preparedStatement.setInt(5, Invoice.getId());
    }

    @Override
    protected void createStatementMapper(InvoiceEntity invoice, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, invoice.getCost());
        preparedStatement.setBoolean(2, invoice.isPaid());
        preparedStatement.setInt(3, invoice.getCashier().getId());
        preparedStatement.setString(4, invoice.getStatus().toString());
    }

    @Override
    protected Optional<InvoiceEntity> mapResultSetToEntity(ResultSet Invoice) throws SQLException {
        return Optional.of(InvoiceEntity.builder()
                .withId(Invoice.getInt(1))
                .withCost(Invoice.getInt(2))
                .withPaid(Invoice.getBoolean(3))
                .withStatus(Status.valueOf(Invoice.getString(5)))
                .build());
    }
}
