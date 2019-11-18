package com.epam.project.service.implementation;

import com.epam.project.dao.PaymentDao;
import com.epam.project.dao.ProductDao;
import com.epam.project.domain.Payment;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataBaseConnectionRuntimeException;
import com.epam.project.exceptions.DataNotFoundRuntimeException;
import com.epam.project.service.PaymentService;
import org.apache.log4j.Logger;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private PaymentDao paymentDao;
    private ProductDao productDao;

    public PaymentServiceImpl(PaymentDao paymentDao, ProductDao productDao) {
        this.paymentDao = paymentDao;
        this.productDao = productDao;
    }

    private boolean validatePayment(Payment payment) {
        return ((payment.getOrderCode() != null)
                && (payment.getProductCode() != null)
                && (!payment.getProductCode().equals(""))
                && (payment.getQuantity() != null)
                && (payment.getStatusId() != null));
    }

    @Override
    public synchronized boolean addPayment(Payment newPayment) {
        if (validatePayment(newPayment)) {
            boolean update = false;
            Product product = productDao.findProductByCode(newPayment.getProductCode());
            List<Payment> payments = paymentDao.findAllPaymentsByOrderCode(newPayment.getOrderCode());
            for (Payment existPayment : payments) {
                if (existPayment.getProductCode().equals(newPayment.getProductCode())) {
                    update = true;
                    product.setQuantity(product.getQuantity() - newPayment.getQuantity());
                    product.setReservedQuantity(product.getReservedQuantity() + newPayment.getQuantity());
                    newPayment.setPaymentId(existPayment.getPaymentId());
                    newPayment.setQuantity(existPayment.getQuantity() + newPayment.getQuantity());
                    newPayment.setPaymentValue(product.getCost() * newPayment.getQuantity());
                }
            }
            if (!update) {
                newPayment.setPaymentValue(product.getCost() * newPayment.getQuantity());
                product.setQuantity(product.getQuantity() - newPayment.getQuantity());
                product.setReservedQuantity(product.getReservedQuantity() + newPayment.getQuantity());
            }
            if ((!productDao.updateProductInDB(product))
                    || (!(update ? paymentDao.updatePaymentInDB(newPayment) : paymentDao.addPaymentToDB(newPayment)))) {
                return false;
            }
            return true;
        }
        return false;
    }
}
