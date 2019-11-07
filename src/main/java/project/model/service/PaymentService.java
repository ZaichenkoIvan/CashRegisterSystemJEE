package project.model.service;

import project.model.domain.Payment;

import java.util.List;

public interface PaymentService {
    boolean createPayment(Payment Payment);

    List<Payment> findPaymentByUser(Integer userId);

    List<Payment> findAll(int currentPage, int recordsPerPage);

    int getNumberOfRows();
}
