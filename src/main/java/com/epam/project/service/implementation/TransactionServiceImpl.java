package com.epam.project.service.implementation;

import com.epam.project.dao.TransactionDao;
import com.epam.project.domain.Payment;
import com.epam.project.domain.Transaction;
import com.epam.project.domain.TransactionType;
import com.epam.project.exceptions.DataNotFoundRuntimeException;
import com.epam.project.exceptions.TransactionServiceRuntimeException;
import com.epam.project.service.TransactionService;
import org.apache.log4j.Logger;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = Logger.getLogger(TransactionServiceImpl.class);
    private TransactionDao transactionDao;

    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public Transaction createTransactionFromPayment(Payment payment, String userName, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setPaymentId(payment.getPaymentId());
        transaction.setInvoiceCode(payment.getOrderCode());
        transaction.setUserName(userName);
        transaction.setTransactionType(type);
        transaction.setPaymentValue(payment.getPaymentValue());
        return transaction;
    }

    @Override
    public List<Transaction> findAllTransactions() throws TransactionServiceRuntimeException {
        List<Transaction> transactions;
        try {
            transactions = transactionDao.findAllTransactions();
        } catch (DataNotFoundRuntimeException ex) {
            LOGGER.error(ex);
            throw new TransactionServiceRuntimeException();
        }
        return transactions;
    }

    @Override
    public List<Transaction> findAllTransactionsByType(TransactionType type) throws TransactionServiceRuntimeException {
        List<Transaction> transactions;
        try {
            transactions = transactionDao.findAllTransactionsByType(type);
        } catch (DataNotFoundRuntimeException ex) {
            LOGGER.error(ex);
            throw new TransactionServiceRuntimeException();
        }
        return transactions;
    }
}
