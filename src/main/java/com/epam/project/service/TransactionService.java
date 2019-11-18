package com.epam.project.service;

import com.epam.project.domain.Payment;
import com.epam.project.domain.Transaction;
import com.epam.project.domain.TransactionType;
import com.epam.project.exceptions.TransactionServiceRuntimeException;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAllTransactions() throws TransactionServiceRuntimeException;

    List<Transaction> findAllTransactionsByType(TransactionType type) throws TransactionServiceRuntimeException;

    Transaction createTransactionFromPayment(Payment payment, String userName, TransactionType type);
}
