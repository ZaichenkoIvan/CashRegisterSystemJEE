package project.model.domain;

import java.util.Objects;

public class Order {
    private final Integer id;
    private final int number;
    private final Product product;
    private final Invoice invoice;

    private Order(OrderBuilder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.product = builder.product;
        this.invoice = builder.invoice;
    }

    public Integer getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public Product getProduct() {
        return product;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return number == order.number &&
                Objects.equals(id, order.id) &&
                Objects.equals(product, order.product) &&
                Objects.equals(invoice, order.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, product, invoice);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", number=" + number +
                ", product=" + product +
                ", invoice=" + invoice +
                '}';
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static final class OrderBuilder {
        private Integer id;
        private int number;
        private Product product;
        private Invoice invoice;

        private OrderBuilder() {
        }

        public OrderBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public OrderBuilder withProduct(Product product) {
            this.product = product;
            return this;
        }

        public OrderBuilder withInvoice(Invoice invoice) {
            this.invoice = invoice;
            return this;
        }

        public OrderBuilder withNumber(int number) {
            this.number = number;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}