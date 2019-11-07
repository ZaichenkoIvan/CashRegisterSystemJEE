package project.model.service.impl;

import org.apache.log4j.Logger;
import project.model.dao.PaymentDao;
import project.model.domain.Payment;
import project.model.entity.PaymentEntity;
import project.model.exception.InvalidEntityCreation;
import project.model.service.PaymentService;
import project.model.service.mapper.PaymentMapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentServiceImpl.class);

    private final PaymentDao paymentDao;
    private final PaymentMapper mapper;

    public PaymentServiceImpl(PaymentDao paymentDao, PaymentMapper mapper) {
        this.paymentDao = paymentDao;
        this.mapper = mapper;
    }

    @Override
    public boolean createPayment(Payment Payment) {
        if (Objects.isNull(Payment) ) {
            LOGGER.warn("Payment is not valid");
            throw new InvalidEntityCreation("Payment is not valid");
        }

        return paymentDao.save(mapper.mapPaymentToPaymentEntity(Payment));
    }

    @Override
    public List<Payment> findPaymentByUser(Integer userId) {
        validateParam(userId);

        return paymentDao.findByUser(userId).stream()
                .map(mapper::mapPaymentEntityToPayment)
                .collect(Collectors.toList());
    }

    private <T> void validateParam(T param) {
        if (Objects.isNull(param)) {
            LOGGER.warn("Parameter is not valid");
            throw new IllegalArgumentException("Parameter is not valid");
        }
    }

    @Override
    public int getNumberOfRows() {
        return paymentDao.getNumberOfRows();
    }

    @Override
    public List<Payment> findAll(int currentPage, int recordsPerPage) {
        List<PaymentEntity> result = paymentDao.findAll(currentPage,recordsPerPage);
        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(mapper::mapPaymentEntityToPayment)
                .collect(Collectors.toList());
    }
}
