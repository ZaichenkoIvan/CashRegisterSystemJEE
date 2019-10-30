package ua.cashregister.model.dao.implementation;

import ua.cashregister.model.dao.GenericAbstractDao;
import ua.cashregister.model.dao.InvoiceDao;
import ua.cashregister.model.dao.MapperFromDB;
import ua.cashregister.model.dao.MapperToDB;
import ua.cashregister.model.dao.exception.DataNotFoundRuntimeException;
import ua.cashregister.model.domain.Invoice;
import ua.cashregister.model.domain.enums.InvoiceStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InvoiceDaoImpl extends GenericAbstractDao<Invoice> implements InvoiceDao {
    private static final String SQL_select_base = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id ";
    private static final String SQL_selectAll = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id;";
    private static final String SQL_selectAllByStatus = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id WHERE invoices.status_id=?;";
    private static final String SQL_selectAllByUserName = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id WHERE user_name=?;";
    private static final String SQL_selectByCode = "SELECT * FROM invoices " +
            "JOIN invoice_status ON invoices.status_id=invoice_status.status_id WHERE invoice_code=?;";
    private static final String SQL_addNew = "INSERT INTO project.invoices (invoice_code, user_id, is_paid, status_id)" +
            " VALUES (?,?,?,?);";
    private static final String SQL_update = "UPDATE project.invoices SET invoice_code=?, user_id=?, is_paid=?, status_id=? " +
            "WHERE invoice_code=?;";
    private static final String SQL_deleteByCode = "DELETE FROM project.invoices WHERE invoice_code=?;";

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

    private MapperFromDB<ResultSet, Invoice> mapperFromDB= (ResultSet resultSet) -> {
        try {
            return Invoice.builder()
                    .withInvoiceId(resultSet.getInt("invoice_id"))
                    .withInvoiceCode(resultSet.getLong("invoice_code"))
                    .withUserId(resultSet.getInt("user_id"))
                    .withIsPaid(resultSet.getBoolean("is_paid"))
                    .withStatus(InvoiceStatus.valueOf(resultSet.getString("status_description")))
                    .withDate(resultSet.getTimestamp("invoice_date"))
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
        return findAll(SQL_selectAll);
    }

    @Override
    public List<Invoice> findInvoices() {
        return findAllFromTo(SQL_select_base);
    }

    @Override
    public List<Invoice> findAllNewInvoices() {
        return findAsListBy(SQL_selectAllByStatus, 0);
    }

    @Override
    public List<Invoice> findAllFinishedInvoices() {
        return findAsListBy(SQL_selectAllByStatus, 1);
    }

    @Override
    public List<Invoice> findAllCancelledInvoices() {
        return findAsListBy(SQL_selectAllByStatus, 2);
    }

    @Override
    public List<Invoice> findAllInvoicesByUser(String userName) {
        return findAsListBy(SQL_selectAllByUserName, userName);
    }

    @Override
    public Invoice findInvoiceByOrderNumber(Long orderCode) {
        return findBy(SQL_selectByCode, orderCode);
    }

    @Override
    public boolean save(Invoice invoice) {
        return addToDB(invoice, SQL_addNew);
    }

    @Override
    public Invoice findById(Integer integer) {
        throw new UnsupportedOperationException("Find by id unsupported operation yet");
    }

    @Override
    public boolean update(Invoice invoice) {
        return updateInDB(invoice, SQL_update, 6, invoice.getInvoiceCode());
    }

    @Override
    public boolean deleteById(Invoice invoice) {
        return deleteFromDB(SQL_deleteByCode, invoice.getInvoiceCode());
    }
}