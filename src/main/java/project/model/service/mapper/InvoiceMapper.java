package project.model.service.mapper;


import project.model.domain.Invoice;
import project.model.domain.User;
import project.model.entity.InvoiceEntity;
import project.model.entity.UserEntity;

public class InvoiceMapper {
    public InvoiceEntity mapInvoiceToInvoiceEntity(Invoice domain) {
        return InvoiceEntity.builder()
                .withId(domain.getId())
                .withCost(domain.getCost())
                .withPaid(domain.isPaid())
                .withStatus(domain.getStatus())
                .withCashier(UserEntity.builder()
                        .withId(domain.getCashier().getId())
                        .build())
                .build();
    }

    public Invoice mapInvoiceEntityToInvoice(InvoiceEntity entity) {
        return Invoice.builder()
                .withId(entity.getId())
                .withCost(entity.getCost())
                .withPaid(entity.isPaid())
                .withStatus(entity.getStatus())
                .withCashier(User.builder()
                        .withId(entity.getCashier().getId())
                        .build())
                .build();
    }
}
