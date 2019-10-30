package ua.cashregister.model.dao;

import ua.cashregister.model.domain.Invoice;

import java.util.List;


public interface InvoiceDao extends CrudDao<Invoice, Integer> {
    List<Invoice> findAllInvoices();

    List<Invoice> findInvoices();

    List<Invoice> findAllNewInvoices();

    List<Invoice> findAllFinishedInvoices();

    List<Invoice> findAllCancelledInvoices();

    List<Invoice> findAllInvoicesByUser(String userName);

    Invoice findInvoiceByOrderNumber(Long orderNum);
}
