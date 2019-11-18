package com.epam.project.dao;

import com.epam.project.domain.Payment;
import com.epam.project.exceptions.DataNotFoundRuntimeException;

import java.util.List;

public interface PaymentDao {

    List<Payment> findAllPaymentsByOrderCode(Long orderCode) throws DataNotFoundRuntimeException;

    Payment findPaymentById(Integer id) throws DataNotFoundRuntimeException;

    boolean addPaymentToDB(Payment payment);

    boolean updatePaymentInDB(Payment payment);

    boolean deletePaymentFromDB(Payment payment);
}
