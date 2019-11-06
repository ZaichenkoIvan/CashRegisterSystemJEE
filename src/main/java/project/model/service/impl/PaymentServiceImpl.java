package project.model.service.impl;

import org.apache.log4j.Logger;
import project.model.dao.PaymentDao;
import project.model.domain.Payment;
import project.model.exception.InvalidEntityCreation;
import project.model.service.PaymentService;
import project.model.service.mapper.PaymentMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentServiceImpl.class);

    private final PaymentDao PaymentDao;
    private final PaymentMapper mapper;

    public PaymentServiceImpl(project.model.dao.PaymentDao PaymentDao, PaymentMapper mapper) {
        this.PaymentDao = PaymentDao;
        this.mapper = mapper;
    }

    @Override
    public boolean createPayment(Payment Payment) {
        if (Objects.isNull(Payment) ) {
            LOGGER.warn("Payment is not valid");
            throw new InvalidEntityCreation("Payment is not valid");
        }

        return PaymentDao.save(mapper.mapPaymentToPaymentEntity(Payment));
    }

    @Override
    public List<Payment> findPaymentByUser(Integer userId) {
        validateParam(userId);

        return PaymentDao.findByUser(userId).stream()
                .map(mapper::mapPaymentEntityToPayment)
                .collect(Collectors.toList());
    }

    private <T> void validateParam(T param) {
        if (Objects.isNull(param)) {
            LOGGER.warn("Parameter is not valid");
            throw new IllegalArgumentException("Parameter is not valid");
        }
    }
}
