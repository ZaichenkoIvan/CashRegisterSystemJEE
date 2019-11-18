package com.epam.project.service.implementation;

import com.epam.project.dao.InvoiceDao;
import com.epam.project.dao.PaymentDao;
import com.epam.project.dao.ProductDao;
import com.epam.project.dao.TransactionDao;
import com.epam.project.domain.*;
import com.epam.project.exceptions.DataBaseConnectionRuntimeException;
import com.epam.project.exceptions.DataNotFoundRuntimeException;
import com.epam.project.exceptions.InvoiceServiceRuntimeException;
import com.epam.project.exceptions.ProductServiceRuntimeException;
import com.epam.project.service.InvoiceService;
import com.epam.project.service.ProductService;
import com.epam.project.service.TransactionService;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvoiceServiceImpl implements InvoiceService {
//TODO: final, delete simply dao to service
    private InvoiceDao invoiceDao;
    private PaymentDao paymentDao;
    private ProductDao productDao;
    private TransactionDao transactionDao;
    private TransactionService transactionService;
    private ProductService productService;

    public InvoiceServiceImpl(InvoiceDao invoiceDao, PaymentDao paymentDao,
                              ProductDao productDao, TransactionDao transactionDao,
                              TransactionService transactionService, ProductService productService) {
        this.invoiceDao = invoiceDao;
        this.paymentDao = paymentDao;
        this.productDao = productDao;
        this.transactionDao = transactionDao;
        this.transactionService = transactionService;
        this.productService = productService;
    }

    @Override
    public List<Invoice> findAllInvoices() {
        List<Invoice> invoices = invoiceDao.findAllInvoices();
        for (Invoice invoice : invoices) {
            if (!addPaymentsToInvoice(invoice, paymentDao, productDao)) {
                throw new DataNotFoundRuntimeException("Invoices not exist");
            }
        }
        invoices.forEach((invoice) -> invoice.setCost(this.calculateTotalCost(invoice)));

        return invoices;
    }

    @Override
    public List<Invoice> findNewInvoices() {
        List<Invoice> invoices = new LinkedList<>();
        invoices = invoiceDao.findAllNewInvoices();
        for (Invoice invoice : invoices) {
            if (!addPaymentsToInvoice(invoice, paymentDao, productDao)) {
                throw new DataNotFoundRuntimeException("New Invoices not exist");
            }
        }
        invoices.forEach((invoice) -> invoice.setCost(this.calculateTotalCost(invoice)));

        return invoices;
    }

    @Override
    public List<Invoice> findFinishedInvoices() {
        List<Invoice> invoices = new LinkedList<>();
        invoices = invoiceDao.findAllFinishedInvoices();
        for (Invoice invoice : invoices) {
            if (!addPaymentsToInvoice(invoice, paymentDao, productDao)) {
                throw new DataNotFoundRuntimeException("Finished Invoices not exist");
            }
        }
        invoices.forEach((invoice) -> invoice.setCost(this.calculateTotalCost(invoice)));

        return invoices;
    }

    @Override
    public List<Invoice> findCancelledInvoices() {
        List<Invoice> invoices = new LinkedList<>();

        invoices = invoiceDao.findAllCancelledInvoices();
        for (Invoice invoice : invoices) {
            if (!addPaymentsToInvoice(invoice, paymentDao, productDao)) {
                throw new DataNotFoundRuntimeException("Cancelled Invoices not exist");
            }
        }
        invoices.forEach((invoice) -> invoice.setCost(this.calculateTotalCost(invoice)));

        return invoices;
    }

    @Override
    public List<Invoice> findInvoicesByUser(String userName) {
        List<Invoice> invoices = invoiceDao.findAllInvoicesByUser(userName);
        for (Invoice invoice : invoices) {
            if (!addPaymentsToInvoice(invoice, paymentDao, productDao)) {
                throw new DataNotFoundRuntimeException("Invoices by user not exist");
            }
        }
        invoices.forEach((invoice) -> invoice.setCost(this.calculateTotalCost(invoice)));
        return invoices;
    }

    @Override
    public Invoice findInvoiceByOrderNumber(Long orderNum) {
        Invoice invoice = invoiceDao.findInvoiceByOrderNumber(orderNum);
        if (!addPaymentsToInvoice(invoice, paymentDao, productDao)) {
            throw new DataNotFoundRuntimeException("Invoices by order number not exist");
        }
        invoice.setCost(this.calculateTotalCost(invoice));
        return invoice;
    }

    @Override
    public boolean addInvoice(Invoice invoice) {
        if (!validateInvoice(invoice))
            return false;
        Set<String> productCodes = invoice.getPayments().keySet();
        if (!invoiceDao.addInvoiceToDB(invoice)) {
            return false;
        }
        for (String productCode : productCodes) {
            Product product = productDao.findProductByCode(productCode);
            Payment payment = invoice.getPayments().get(productCode);
            if ((product.getQuantity() - payment.getQuantity()) < 0) {
                return false;
            }
            product.setQuantity(product.getQuantity() - payment.getQuantity());
            product.setReservedQuantity(product.getReservedQuantity() + payment.getQuantity());
            if ((!paymentDao.addPaymentToDB(invoice.getPayments().get(productCode)))
                    || (!productDao.updateProductInDB(product))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public synchronized boolean updateInvoice(Invoice invoice) {
        if (!validateInvoice(invoice))
            return false;
        Invoice oldInvoice = findInvoiceByOrderNumber(invoice.getInvoiceCode());
        if (!deleteInvoice(oldInvoice))
            return false;
        if (!addInvoice(invoice)) {
            addInvoice(oldInvoice);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteInvoice(Long orderCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        return !((invoice == null) || (invoice.getStatus() != InvoiceStatus.CREATED) || (invoice.getPaid()))
                && deleteInvoice(invoice);
    }

    private synchronized boolean deleteInvoice(Invoice invoice) {
        Set<String> productCodes = invoice.getPayments().keySet();
        for (String productCode : productCodes) {
            Product product = productDao.findProductByCode(productCode);
            Payment payment = invoice.getPayments().get(productCode);
            product.setQuantity(product.getQuantity() + payment.getQuantity());
            product.setReservedQuantity(product.getReservedQuantity() - payment.getQuantity());
            if ((!paymentDao.deletePaymentFromDB(payment)) || (!productDao.updateProductInDB(product))) {
                return false;
            }
        }
        if (!invoiceDao.deleteInvoiceFromDB(invoice)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean cancelInvoice(Long orderCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        return !((invoice == null) || (invoice.getStatus() != InvoiceStatus.CREATED)) && cancelInvoice(invoice);
    }

    private boolean cancelInvoice(Invoice invoice) {
        Set<String> productCodes = invoice.getPayments().keySet();
        for (String productCode : productCodes) {
            Product product = productDao.findProductByCode(productCode);
            Payment payment = paymentDao.findPaymentById(invoice.getPayments().get(productCode).getPaymentId());
            product.setReservedQuantity(product.getReservedQuantity() - payment.getQuantity());
            product.setQuantity(product.getQuantity() + payment.getQuantity());
            payment.setStatusId(InvoiceStatus.CANCELLED);
            if (invoice.getPaid()) {
                Transaction refund = transactionService.createTransactionFromPayment(payment, invoice.getUserName(), TransactionType.REFUND);
                if (!transactionDao.addTransactionToDB(refund)) {
                    return false;
                }
            }
            if ((!productDao.updateProductInDB(product)) || (!paymentDao.updatePaymentInDB(payment))) {
                return false;
            }
        }
        invoice.setStatus(InvoiceStatus.CANCELLED);
        return invoiceDao.updateInvoiceInDB(invoice);
    }

    @Override
    public boolean removeProductFromInvoice(Long orderCode, String productCode) {
        Invoice invoice = findInvoiceByOrderNumber(orderCode);
        return !((invoice == null) || (invoice.getStatus() != InvoiceStatus.CREATED))
                && removeProductFromInvoice(invoice, productCode);
    }

    //TODO: new method
    private boolean removeProductFromInvoice(Invoice invoice, String productCode) {
        if (invoice.getProducts().containsKey(productCode)
                && invoice.getPayments().containsKey(productCode)) {
            Payment payment = invoice.getPayments().get(productCode);
            invoice.getPayments().remove(productCode);
            invoice.getProducts().remove(productCode);
            return removePayment(payment);
        }
        return false;
    }

    @Override
    public boolean confirmPayment(Long invoiceCode) {
        return payByInvoice(invoiceCode);
    }

    @Override
    public boolean payByInvoice(Long invoiceCode) {
        Invoice invoice = findInvoiceByOrderNumber(invoiceCode);
        return !((invoice == null) || (invoice.getPaid()) || (invoice.getStatus() != InvoiceStatus.CREATED))
                && payByInvoice(invoice);
    }

    private boolean payByInvoice(Invoice invoice) {
        Set<String> products = invoice.getProducts().keySet();
        for (String productCode : products) {
            Payment payment = invoice.getPayments().get(productCode);
            Transaction transaction = transactionService.createTransactionFromPayment(payment, invoice.getUserName(), TransactionType.PAYMENT);
            if (!transactionDao.addTransactionToDB(transaction)) {
                return false;
            }
        }
        invoice.setPaid(true);
        return invoiceDao.updateInvoiceInDB(invoice);
    }

    @Override
    public boolean closeInvoice(Long invoiceCode) {
        Invoice invoice = findInvoiceByOrderNumber(invoiceCode);
        return !((invoice == null) || (invoice.getStatus() != InvoiceStatus.CREATED)) && closeInvoice(invoice);
    }

    private synchronized boolean closeInvoice(Invoice invoice) {
        Set<String> productCodes = invoice.getPayments().keySet();
        for (String productCode : productCodes) {
            Product product = productDao.findProductByCode(productCode);
            Payment payment = paymentDao.findPaymentById(invoice.getPayments().get(productCode).getPaymentId());
            product.setReservedQuantity(product.getReservedQuantity() - payment.getQuantity());
            payment.setStatusId(InvoiceStatus.FINISHED);
            if ((!productDao.updateProductInDB(product)) || (!paymentDao.updatePaymentInDB(payment))) {
                return false;
            }
        }
        invoice.setStatus(InvoiceStatus.FINISHED);
        if (!invoiceDao.updateInvoiceInDB(invoice)) {
            return false;
        }
        return true;
    }

    @Override
    public Invoice createInvoiceFromUserCart(UserCart userCart, Long orderCode) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceCode(orderCode);
        invoice.setUserName(userCart.getUserName());
        invoice.setPaid(false);
        invoice.setStatus(InvoiceStatus.CREATED);
        for (Map.Entry<String, Double> unit : userCart.getProducts().entrySet()) {
            Product product;
            product = productService.findProductByCode(unit.getKey());
            invoice.addProduct(product);
            Payment payment = new Payment();
            payment.setOrderCode(orderCode);
            payment.setProductCode(unit.getKey());
            payment.setQuantity(unit.getValue());
            payment.setStatusId(InvoiceStatus.CREATED);
            invoice.addPayment(payment);
        }
        return invoice;
    }

    public UserCartView createUsersCartView(UserCart userCart) {
        UserCartView view = new UserCartView(userCart.getUserName());
        Double cost = 0d;
        view.setOrderNotes(userCart.getOrderNotes());
        for (Map.Entry<String, Double> unit : userCart.getProducts().entrySet()) {
            Product product = productService.findProductByCode(unit.getKey());
            view.addProduct(product, unit.getValue());
            cost += product.getCost() * unit.getValue();
        }
        view.setTotalCost(cost);

        return view;
    }

    private boolean validateInvoice(Invoice invoice) {
        if ((invoice.getInvoiceCode() == null)
                || (invoice.getPayments().size() == 0)
                || (invoice.getProducts().size() == 0)
                || (invoice.getPayments().size() != invoice.getProducts().size()))
            return false;
        Long orderCode = invoice.getInvoiceCode();
        InvoiceStatus status = invoice.getStatus();
        for (Map.Entry<String, Payment> paymentEntry : invoice.getPayments().entrySet())
            if (!paymentEntry.getValue().getOrderCode().equals(orderCode)
                    || (paymentEntry.getValue().getStatusId() != status))
                return false;
        return true;
    }

    private synchronized boolean removePayment(Payment payment) {
        Double quantity = payment.getQuantity();
        Product product = productDao.findProductByCode(payment.getProductCode());
        product.setQuantity(product.getQuantity() + quantity);
        product.setReservedQuantity(product.getReservedQuantity() - quantity);
        if ((!paymentDao.deletePaymentFromDB(payment))
                || (!productDao.updateProductInDB(product))) {
            return false;
        }
        return true;
    }

    private synchronized boolean addPaymentsToInvoice(Invoice invoice, PaymentDao paymentDao, ProductDao productDao) {
        List<Payment> payments;
        payments = paymentDao.findAllPaymentsByOrderCode(invoice.getInvoiceCode());
        if (payments == null)
            return false;
        for (Payment payment : payments) {
            String productCode = payment.getProductCode();
            Product product = productDao.findProductByCode(productCode);
            invoice.addProduct(product);
            invoice.addPayment(payment);
        }
        return true;
    }

    private synchronized Double calculateTotalCost(Invoice invoice) {
        Double result = 0d;
        for (Map.Entry<String, Payment> paymentEntry : invoice.getPayments().entrySet())
            result += paymentEntry.getValue().getPaymentValue();
        return result;
    }
}
