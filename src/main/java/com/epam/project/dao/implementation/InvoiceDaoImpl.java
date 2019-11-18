package com.epam.project.dao.implementation;
import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.InvoiceDao;
import com.epam.project.dao.Mapper;
import com.epam.project.domain.*;
import com.epam.project.exceptions.DataNotFoundRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class InvoiceDaoImpl extends GenericAbstractDao<Invoice> implements InvoiceDao {

    private Connection connection;
    private static final String SQL_SELECT_ALL = "SELECT * FROM projectcash.invoices " +
            "JOIN projectcash.invoice_status ON invoices.status_id=invoice_status.status_id;";
    private static final String SQL_SELECT_ALL_BY_STATUS = "SELECT * FROM projectcash.invoices " +
            "JOIN projectcash.invoice_status ON invoices.status_id=invoice_status.status_id WHERE invoices.status_id=?;";
    private static final String SQL_SELECT_ALL_BY_USER_NAME = "SELECT * FROM projectcash.invoices " +
            "JOIN projectcash.invoice_status ON invoices.status_id=invoice_status.status_id WHERE user_name=?;";
    private static final String SQL_SELECT_BY_CODE = "SELECT * FROM projectcash.invoices " +
            "JOIN projectcash.invoice_status ON invoices.status_id=invoice_status.status_id WHERE invoice_code=?;";
    private static final String SQL_ADD_NEW = "INSERT INTO projectcash.invoices (invoice_code, user_name, is_paid, status_id, invoice_notes)" +
            " VALUES (?,?,?,?,?);";
    private static final String SQL_UPDATE = "UPDATE projectcash.invoices SET invoice_code=?, user_name=?, is_paid=?, status_id=?, invoice_notes=? " +
            "WHERE invoice_code=?;";
    private static final String SQL_DELETE_BY_CODE = "DELETE FROM projectcash.invoices WHERE invoice_code=?;";

    //TODO:Mapper const

    private Mapper<Invoice, PreparedStatement> mapperToDB = (Invoice invoice, PreparedStatement preparedStatement) -> {
        preparedStatement.setLong(1, invoice.getInvoiceCode());
        preparedStatement.setString(2, invoice.getUserName());
        preparedStatement.setBoolean(3, invoice.getPaid());
        preparedStatement.setInt(4, invoice.getStatus().ordinal());
        preparedStatement.setString(5, invoice.getInvoiceNotes());
    };

    private Mapper<ResultSet, Invoice> mapperFromDB = (ResultSet resultSet, Invoice invoice) -> {
        invoice.setInvoiceId(resultSet.getInt("invoice_id"));
        invoice.setInvoiceCode(resultSet.getLong("invoice_code"));
        invoice.setUserName(resultSet.getString("user_name"));
        invoice.setPaid(resultSet.getBoolean("is_paid"));
        invoice.setStatus(InvoiceStatus.valueOf(resultSet.getString("status_description")));
        invoice.setDate(resultSet.getTimestamp("invoice_date"));
        invoice.setInvoiceNotes(resultSet.getString("invoice_notes"));
    };

    public InvoiceDaoImpl(Connection connection) {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
        this.connection = connection;
    }

    @Override
    public List<Invoice> findAllInvoices() throws DataNotFoundRuntimeException {
        return findAll(connection, Invoice.class, SQL_SELECT_ALL);
    }

    @Override
    public List<Invoice> findAllNewInvoices() throws DataNotFoundRuntimeException {
        return findAsListBy(connection, Invoice.class, SQL_SELECT_ALL_BY_STATUS, 0);
    }

    @Override
    public List<Invoice> findAllFinishedInvoices() throws DataNotFoundRuntimeException {
        return findAsListBy(connection, Invoice.class, SQL_SELECT_ALL_BY_STATUS, 1);
    }

    @Override
    public List<Invoice> findAllCancelledInvoices() throws DataNotFoundRuntimeException {
        return findAsListBy(connection, Invoice.class, SQL_SELECT_ALL_BY_STATUS, 2);
    }

    @Override
    public List<Invoice> findAllInvoicesByUser(String userName) throws DataNotFoundRuntimeException {
        return findAsListBy(connection, Invoice.class, SQL_SELECT_ALL_BY_USER_NAME, userName);
    }

    @Override
    public Invoice findInvoiceByOrderNumber(Long orderCode) throws DataNotFoundRuntimeException {
        return findBy(connection, Invoice.class, SQL_SELECT_BY_CODE, orderCode);
    }

    @Override
    public boolean addInvoiceToDB(Invoice invoice) {
        return addToDB(connection, invoice, SQL_ADD_NEW);
    }

    @Override
    public boolean updateInvoiceInDB(Invoice invoice) {
        return updateInDB(connection, invoice, SQL_UPDATE, 6, invoice.getInvoiceCode());
    }

    @Override
    public boolean deleteInvoiceFromDB(Invoice invoice) {
        return deleteFromDB(connection, SQL_DELETE_BY_CODE, invoice.getInvoiceCode());
    }
}
