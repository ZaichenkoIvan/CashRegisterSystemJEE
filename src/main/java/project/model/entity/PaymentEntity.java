package project.model.entity;

import java.util.Objects;

public class PaymentEntity {
    private final Integer id;
    private final int paymentValue;
    private final InvoiceEntity invoiceEntity;
    private final UserEntity user;

    public PaymentEntity(Integer id, int paymentValue, InvoiceEntity invoiceEntity, UserEntity user) {
        this.id = id;
        this.paymentValue = paymentValue;
        this.invoiceEntity = invoiceEntity;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public int getPaymentValue() {
        return paymentValue;
    }

    public InvoiceEntity getInvoiceEntity() {
        return invoiceEntity;
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "PaymentEntity{" +
                "id=" + id +
                ", paymentValue=" + paymentValue +
                ", invoiceEntity=" + invoiceEntity +
                ", servlet=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentEntity that = (PaymentEntity) o;
        return paymentValue == that.paymentValue &&
                Objects.equals(id, that.id) &&
                Objects.equals(invoiceEntity, that.invoiceEntity) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentValue, invoiceEntity, user);
    }
}
