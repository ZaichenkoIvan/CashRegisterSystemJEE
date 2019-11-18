package com.epam.project.dao.implementation;

import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.PaymentDao;
import com.epam.project.dao.Mapper;
import com.epam.project.domain.InvoiceStatus;
import com.epam.project.domain.Payment;
import com.epam.project.exceptions.DataNotFoundRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PaymentDaoImpl extends GenericAbstractDao<Payment> implements PaymentDao {

    private Connection connection;
    private static final String SQL_SELECT_BY_ORDER_CODE = "SELECT * FROM projectcash.payments " +
            "JOIN projectcash.invoice_status ON payments.status_id=invoice_status.status_id WHERE invoice_code=?;";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM projectcash.payments " +
            "JOIN projectcash.invoice_status ON payments.status_id=invoice_status.status_id WHERE payment_id=?;";
    private static final String SQL_ADD_NEW_PAYMENT = "INSERT INTO projectcash.payments (invoice_code, product_code, quantity," +
            "payment_value, status_id, payment_notes) VALUES (?,?,?,?,?,?);";
    private static final String SQL_UPDATE_PAYMENT = "UPDATE projectcash.payments SET invoice_code=?, product_code=?, quantity=?," +
            "payment_value=?, status_id=?, payment_notes=? WHERE payment_id=?;";
    private static final String SQL_DELETE_PAYMENT_BY_ID = "DELETE FROM projectcash.payments WHERE payment_id=?;";

    private Mapper<Payment, PreparedStatement> mapperToDB = (Payment payment, PreparedStatement preparedStatement) -> {
        preparedStatement.setLong(1, payment.getOrderCode());
        preparedStatement.setString(2, payment.getProductCode());
        preparedStatement.setDouble(3, payment.getQuantity());
        preparedStatement.setDouble(4, payment.getPaymentValue());
        preparedStatement.setInt(5, payment.getStatusId().ordinal());
        preparedStatement.setString(6, payment.getPaymentNotes());
    };

    private Mapper<ResultSet, Payment> mapperFromDB = (ResultSet resultSet, Payment payment) -> {
        payment.setPaymentId(resultSet.getInt("payment_id"));
        payment.setOrderCode(resultSet.getLong("invoice_code"));
        payment.setProductCode(resultSet.getString("product_code"));
        payment.setQuantity(resultSet.getDouble("quantity"));
        payment.setPaymentValue(resultSet.getDouble("payment_value"));
        payment.setStatusId(InvoiceStatus.valueOf(resultSet.getString("status_description")));
        payment.setPaymentNotes(resultSet.getString("payment_notes"));
    };

    public PaymentDaoImpl(Connection connection) {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
        this.connection = connection;
    }

    @Override
    public List<Payment> findAllPaymentsByOrderCode(Long orderCode) throws DataNotFoundRuntimeException {
        return findAsListBy(this.connection, Payment.class, SQL_SELECT_BY_ORDER_CODE, orderCode);
    }

    @Override
    public Payment findPaymentById(Integer id) throws DataNotFoundRuntimeException {
        return findBy(this.connection, Payment.class, SQL_SELECT_BY_ID, id);
    }

    @Override
    public boolean addPaymentToDB(Payment payment) {
        return addToDB(this.connection, payment, SQL_ADD_NEW_PAYMENT);
    }

    @Override
    public boolean updatePaymentInDB(Payment payment) {
        return updateInDB(connection, payment, SQL_UPDATE_PAYMENT, 7, payment.getPaymentId());
    }

    @Override
    public boolean deletePaymentFromDB(Payment payment) {
        return deleteFromDB(connection, SQL_DELETE_PAYMENT_BY_ID, payment.getPaymentId());
    }
}
