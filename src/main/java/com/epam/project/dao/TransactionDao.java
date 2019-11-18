package com.epam.project.dao;

import com.epam.project.domain.Transaction;
import com.epam.project.domain.TransactionType;
import com.epam.project.exceptions.DataNotFoundRuntimeException;

import java.util.List;

public interface TransactionDao {

    List<Transaction> findAllTransactions() throws DataNotFoundRuntimeException;

    List<Transaction> findAllTransactionsByType(TransactionType type) throws DataNotFoundRuntimeException;

    boolean addTransactionToDB(Transaction transaction);
}
