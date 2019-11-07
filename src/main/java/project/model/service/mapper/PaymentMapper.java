package project.model.service.mapper;

import project.model.domain.Invoice;
import project.model.domain.Payment;
import project.model.domain.User;
import project.model.entity.InvoiceEntity;
import project.model.entity.PaymentEntity;
import project.model.entity.UserEntity;

public class PaymentMapper {
    public PaymentEntity mapPaymentToPaymentEntity(Payment domain) {
        return new PaymentEntity(domain.getId(), domain.getPaymentValue(),
                InvoiceEntity.builder()
                        .withId(domain.getInvoice().getId())
                        .build(),
                UserEntity.builder()
                        .withId(domain.getUser().getId())
                        .build());
    }

    public Payment mapPaymentEntityToPayment(PaymentEntity entity) {
        return new Payment(entity.getId(), entity.getPaymentValue(),
                Invoice.builder()
                        .withId(entity.getInvoiceEntity().getId())
                        .build(),
                User.builder()
                        .withId(entity.getUser().getId())
                        .build());
    }
}
