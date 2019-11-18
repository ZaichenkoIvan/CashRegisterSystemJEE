package com.epam.project.dao;

import com.epam.project.domain.Invoice;

import java.util.List;

public interface InvoiceDao {

    List<Invoice> findAllInvoices();

    List<Invoice> findAllNewInvoices();

    List<Invoice> findAllFinishedInvoices();

    List<Invoice> findAllCancelledInvoices();

    List<Invoice> findAllInvoicesByUser(String userName);

    Invoice findInvoiceByOrderNumber(Long orderNum);

    boolean addInvoiceToDB(Invoice invoice);

    boolean updateInvoiceInDB(Invoice invoice);

    boolean deleteInvoiceFromDB(Invoice invoice);
}
