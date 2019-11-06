package project.model.domain;

import project.model.entity.enums.Status;

import java.util.List;
import java.util.Objects;

public class Invoice {
    private final Integer id;
    private final int cost;
    private final boolean isPaid;
    private final Status status;
    private final User cashier;
    private final List<Order> orders;

    private Invoice(InvoiceBuilder builder) {
        this.id = builder.id;
        this.cost = builder.cost;
        this.isPaid = builder.isPaid;
        this.status = builder.status;
        this.cashier = builder.cashier;
        this.orders = builder.orderEntities;
    }

    public Integer getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public Status getStatus() {
        return status;
    }

    public User getCashier() {
        return cashier;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public static InvoiceBuilder builder() {
        return new InvoiceBuilder();
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", cost=" + cost +
                ", isPaid=" + isPaid +
                ", status=" + status +
                ", cashier=" + cashier +
                ", orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice that = (Invoice) o;
        return cost == that.cost &&
                isPaid == that.isPaid &&
                Objects.equals(id, that.id) &&
                status == that.status &&
                Objects.equals(cashier, that.cashier) &&
                Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, isPaid, status, cashier, orders);
    }

    public static final class InvoiceBuilder {
        private Integer id;
        private int cost;
        private boolean isPaid;
        private Status status;
        private User cashier;
        private List<Order> orderEntities;

        private InvoiceBuilder() {
        }

        public InvoiceBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public InvoiceBuilder withCost(int cost) {
            this.cost = cost;
            return this;
        }

        public InvoiceBuilder withPaid(boolean paid) {
            isPaid = paid;
            return this;
        }

        public InvoiceBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public InvoiceBuilder withCashier(User cashier) {
            this.cashier = cashier;
            return this;
        }

        public InvoiceBuilder withOrderEntities(List<Order> orderEntities) {
            this.orderEntities = orderEntities;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
