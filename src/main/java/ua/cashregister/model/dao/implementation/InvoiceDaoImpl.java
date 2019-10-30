package ua.cashregister.model.dao.implementation;

import ua.cashregister.model.dao.GenericAbstractDao;
import ua.cashregister.model.dao.InvoiceDao;
import ua.cashregister.model.dao.mapper.MapperFromDB;
import ua.cashregister.model.dao.mapper.MapperToDB;
import ua.cashregister.model.dao.exception.DataNotFoundRuntimeException;
import ua.cashregister.model.domain.Invoice;
import ua.cashregister.model.domain.enums.InvoiceStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InvoiceDaoImpl extends GenericAbstractDao<Invoice> implements InvoiceDao {
    private static final String SELECT_BASE = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id ";
    private static final String SELECT_ALL_INVOICES = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id;";
    private static final String SELECT_ALL_INVOICES_BY_STATUS = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id WHERE invoices.status_id=?;";
    private static final String SELECT_ALL_INVOICES_BY_USER_NAME = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id WHERE user_name=?;";
    private static final String SELECT_INVOICE_BY_CODE = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id WHERE invoice_code=?;";
    private static final String INSERT_INVOICE = "INSERT INTO project.invoices (invoice_code, user_id, is_paid, status_id)" +
            " VALUES (?,?,?,?);";
    private static final String INSERT_ORDER = "INSERT INTO project.orders (invoice_id, product_id)" +
            " VALUES (?,?);";
    private static final String UPDATE_INVOICE = "UPDATE project.invoices SET invoice_code=?, user_id=?, is_paid=?, status_id=? " +
            "WHERE invoice_code=?;";
    private static final String DELETE_INVOICE_BY_CODE = "DELETE FROM project.invoices WHERE invoice_code=?;";

    private MapperToDB<Invoice, PreparedStatement> mapperToDB = (Invoice invoice, PreparedStatement preparedStatement) -> {
        try {
            preparedStatement.setLong(1, invoice.getInvoiceCode());
            preparedStatement.setInt(2, invoice.getUserId());
            preparedStatement.setBoolean(3, invoice.getPaid());
            preparedStatement.setInt(4, invoice.getStatus().ordinal());

        } catch (SQLException e) {
            throw new DataNotFoundRuntimeException();
        }
    };

    private MapperFromDB<ResultSet, Invoice> mapperFromDB = (ResultSet resultSet) -> {
        try {
            return Invoice.builder()
                    .withInvoiceId(resultSet.getInt("invoice_id"))
                    .withInvoiceCode(resultSet.getLong("invoice_code"))
                    .withUserId(resultSet.getInt("user_id"))
                    .withIsPaid(resultSet.getBoolean("is_paid"))
                    .withStatus(InvoiceStatus.valueOf(resultSet.getString("status_description")))
                    .build();
        } catch (SQLException e) {
            throw new DataNotFoundRuntimeException();
        }
    };

    public InvoiceDaoImpl() {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        return findAll(SELECT_ALL_INVOICES);
    }

    @Override
    public List<Invoice> findInvoices() {
        return findAllFromTo(SELECT_BASE);
    }

    @Override
    public List<Invoice> findAllNewInvoices() {
        return findAsListBy(SELECT_ALL_INVOICES_BY_STATUS, 0);
    }

    @Override
    public List<Invoice> findAllFinishedInvoices() {
        return findAsListBy(SELECT_ALL_INVOICES_BY_STATUS, 1);
    }

    @Override
    public List<Invoice> findAllCancelledInvoices() {
        return findAsListBy(SELECT_ALL_INVOICES_BY_STATUS, 2);
    }

    @Override
    public List<Invoice> findAllInvoicesByUser(String userName) {
        return findAsListBy(SELECT_ALL_INVOICES_BY_USER_NAME, userName);
    }

    @Override
    public Invoice findInvoiceByOrderNumber(Long orderCode) {
        return findBy(SELECT_INVOICE_BY_CODE, orderCode);
    }

    @Override
    public boolean save(Invoice invoice) {
        boolean result;
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatementInsertInvoice = connection.prepareStatement(INSERT_INVOICE);
             PreparedStatement preparedStatementInsertListProduct = connection.prepareStatement(INSERT_ORDER)){
            mapperToDB.map(invoice, preparedStatementInsertInvoice);
            result = preparedStatementInsertInvoice.executeUpdate() > 0;


        } catch (SQLException sqle) {
            log.error(sqle);
            return false;
        }
        return result;
    }

    @Override
    public Invoice findById(Integer integer) {
        throw new UnsupportedOperationException("Find by id unsupported operation yet");
    }

    @Override
    public boolean update(Invoice invoice) {
        return updateInDB(invoice, UPDATE_INVOICE, 6, invoice.getInvoiceCode());
    }

    @Override
    public boolean deleteById(Invoice invoice) {
        return deleteFromDB(DELETE_INVOICE_BY_CODE, invoice.getInvoiceCode());
    }
}
