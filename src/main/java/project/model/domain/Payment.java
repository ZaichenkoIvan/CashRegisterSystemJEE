package project.model.domain;

import java.util.Objects;

public class Payment {
    private final Integer id;
    private final int paymentValue;
    private final Invoice invoice;
    private final User user;

    public Payment(Integer id, int paymentValue, Invoice invoice, User user) {
        this.id = id;
        this.paymentValue = paymentValue;
        this.invoice = invoice;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public int getPaymentValue() {
        return paymentValue;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentValue=" + paymentValue +
                ", invoice=" + invoice +
                ", servlet=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment that = (Payment) o;
        return paymentValue == that.paymentValue &&
                Objects.equals(id, that.id) &&
                Objects.equals(invoice, that.invoice) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentValue, invoice, user);
    }
}
