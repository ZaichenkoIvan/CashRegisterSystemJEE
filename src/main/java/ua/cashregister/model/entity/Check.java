package ua.cashregister.model.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Check {
    private final int id;
    private final Timestamp createTime;
    private final User cashier;
    private final long totalPrice;
    private final List<Product> products;
    private final Shift shift;

    private Check(CheckBuilder builder) {
        this.id = builder.id;
        this.createTime = builder.createTime;
        this.cashier = builder.cashier;
        this.totalPrice = builder.totalPrice;
        this.products = builder.products;
        this.shift = builder.shift;
    }

    public static CheckBuilder builder() {
        return new CheckBuilder();
    }

    public int getId() {
        return id;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public User getCashier() {
        return cashier;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Shift getShift() {
        return shift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return id == check.id &&
                totalPrice == check.totalPrice &&
                Objects.equals(createTime, check.createTime) &&
                Objects.equals(cashier, check.cashier) &&
                Objects.equals(products, check.products) &&
                Objects.equals(shift, check.shift);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime, cashier, totalPrice, products, shift);
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", createTime='" + createTime +
                ", cashier=" + cashier.getEmail() +
                ", totalPrice=" + totalPrice +
                ", shift=" + shift.getId() +
                '}';
    }


    public static final class CheckBuilder {
        private int id;
        private Timestamp createTime;
        private User cashier;
        private long totalPrice;
        private List<Product> products;
        private Shift shift;

        private CheckBuilder() {
        }

        public static CheckBuilder aCheck() {
            return new CheckBuilder();
        }

        public CheckBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public CheckBuilder withCreateTime(Timestamp createTime) {
            this.createTime = createTime;
            return this;
        }

        public CheckBuilder withCashier(User cashier) {
            this.cashier = cashier;
            return this;
        }

        public CheckBuilder withTotalPrice(long totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public CheckBuilder withProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public CheckBuilder withShift(Shift shift) {
            this.shift = shift;
            return this;
        }

        public Check build() {
            return new Check(this);
        }
    }
}
