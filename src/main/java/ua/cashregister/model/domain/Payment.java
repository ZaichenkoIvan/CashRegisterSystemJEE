package ua.cashregister.model.domain;

import java.util.Objects;

public class Payment {
    private final Integer id;
    private final Double paymentValue;
    private final Invoice invoice;
    private final User user;

    public Payment(Integer id, Double paymentValue, Invoice invoice, User user) {
        this.id = id;
        this.paymentValue = paymentValue;
        this.invoice = invoice;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public Double getPaymentValue() {
        return paymentValue;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
                Objects.equals(paymentValue, payment.paymentValue) &&
                Objects.equals(invoice, payment.invoice) &&
                Objects.equals(user, payment.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentValue, invoice, user);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentValue=" + paymentValue +
                ", invoice=" + invoice +
                ", user=" + user +
                '}';
    }
}
