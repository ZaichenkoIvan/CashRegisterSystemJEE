package project.model.entity;

import project.model.entity.enums.Status;

import java.util.List;
import java.util.Objects;

public class InvoiceEntity {
    private final Integer id;
    private final int cost;
    private final boolean isPaid;
    private final Status status;
    private final UserEntity cashier;
    private final List<OrderEntity> orderEntities;

    private InvoiceEntity(InvoiceBuilder builder) {
        this.id = builder.id;
        this.cost = builder.cost;
        this.isPaid = builder.isPaid;
        this.status = builder.status;
        this.cashier = builder.cashier;
        this.orderEntities = builder.orderEntities;
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

    public UserEntity getCashier() {
        return cashier;
    }

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public static InvoiceBuilder builder() {
        return new InvoiceBuilder();
    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "id=" + id +
                ", cost=" + cost +
                ", isPaid=" + isPaid +
                ", status=" + status +
                ", cashier=" + cashier +
                ", orderEntities=" + orderEntities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceEntity that = (InvoiceEntity) o;
        return cost == that.cost &&
                isPaid == that.isPaid &&
                Objects.equals(id, that.id) &&
                status == that.status &&
                Objects.equals(cashier, that.cashier) &&
                Objects.equals(orderEntities, that.orderEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, isPaid, status, cashier, orderEntities);
    }

    public static final class InvoiceBuilder {
        private Integer id;
        private int cost;
        private boolean isPaid;
        private Status status;
        private UserEntity cashier;
        private List<OrderEntity> orderEntities;

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

        public InvoiceBuilder withCashier(UserEntity cashier) {
            this.cashier = cashier;
            return this;
        }

        public InvoiceBuilder withOrderEntities(List<OrderEntity> orderEntities) {
            this.orderEntities = orderEntities;
            return this;
        }

        public InvoiceEntity build() {
            return new InvoiceEntity(this);
        }
    }
}
