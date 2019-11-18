package com.epam.project.dao.implementation;

import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.Mapper;
import com.epam.project.dao.TransactionDao;
import com.epam.project.domain.Transaction;
import com.epam.project.domain.TransactionType;
import com.epam.project.exceptions.DataNotFoundRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TransactionDaoImpl extends GenericAbstractDao<Transaction> implements TransactionDao {

    private Connection connection;
    private static final String SQL_SELECT_ALL = "SELECT * FROM projectcash.transactions;";
    private static final String SQL_SELECT_BY_TYPE = "SELECT * FROM projectcash.transactions WHERE transaction_type=?;";
    private static final String SQL_ADD_NEW = "INSERT INTO projectcash.transactions " +
            "(payment_id, invoice_code, user_name, transaction_type, payment_value, transaction_notes) " +
            "VALUES (?, ?, ?, ?, ?, ?);";


    private Mapper<Transaction, PreparedStatement> mapperToDB = (Transaction transaction, PreparedStatement ps) -> {
        ps.setInt(1, transaction.getPaymentId());
        ps.setLong(2, transaction.getInvoiceCode());
        ps.setString(3, transaction.getUserName());
        ps.setString(4, transaction.getTransactionType().name());
        ps.setDouble(5, transaction.getPaymentValue());
        ps.setString(6, transaction.getNotes());
    };

    private Mapper<ResultSet, Transaction> mapperFromDB = (ResultSet resultSet, Transaction transaction) -> {
        transaction.setTransactionId(resultSet.getInt("transaction_id"));
        transaction.setPaymentId(resultSet.getInt("payment_id"));
        transaction.setInvoiceCode(resultSet.getLong("invoice_code"));
        transaction.setUserName(resultSet.getString("user_name"));
        transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
        transaction.setPaymentValue(resultSet.getDouble("payment_value"));
        transaction.setTime(resultSet.getTimestamp("transaction_time"));
        transaction.setNotes(resultSet.getString("transaction_notes"));
    };

    public TransactionDaoImpl(Connection connection) {
        this.connection = connection;
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }

    @Override
    public List<Transaction> findAllTransactions() throws DataNotFoundRuntimeException {
        return findAll(this.connection, Transaction.class, SQL_SELECT_ALL);
    }

    @Override
    public List<Transaction> findAllTransactionsByType(TransactionType type) throws DataNotFoundRuntimeException {
        return findAsListBy(this.connection, Transaction.class, SQL_SELECT_BY_TYPE, type.toString());
    }

    @Override
    public boolean addTransactionToDB(Transaction transaction) {
        return addToDB(this.connection, transaction, SQL_ADD_NEW);
    }
}
