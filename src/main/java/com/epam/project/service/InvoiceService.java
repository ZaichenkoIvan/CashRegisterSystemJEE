package com.epam.project.service;

import com.epam.project.domain.Invoice;
import com.epam.project.domain.UserCart;
import com.epam.project.domain.UserCartView;

import java.util.List;

public interface InvoiceService {
    List<Invoice> findAllInvoices();

    List<Invoice> findNewInvoices();

    List<Invoice> findFinishedInvoices();

    List<Invoice> findCancelledInvoices();

    List<Invoice> findInvoicesByUser(String userName);

    Invoice findInvoiceByOrderNumber(Long orderNum);

    Invoice createInvoiceFromUserCart(UserCart userCart, Long orderCode);

    UserCartView createUsersCartView(UserCart userCart);

    boolean addInvoice(Invoice invoice);

    boolean updateInvoice(Invoice invoice);

    boolean deleteInvoice(Long orderCode);

    boolean cancelInvoice(Long orderCode);

    boolean closeInvoice(Long orderCode);

    boolean removeProductFromInvoice(Long orderCode, String productCode);

    boolean confirmPayment(Long invoiceCode);

    boolean payByInvoice(Long invoiceCode);
}
