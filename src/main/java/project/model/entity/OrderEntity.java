package project.model.entity;

import java.util.Objects;

public class OrderEntity {
    private final Integer id;
    private final int number;
    private final ProductEntity productEntity;
    private final InvoiceEntity invoiceEntity;

    private OrderEntity(OrderBuilder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.productEntity = builder.productEntity;
        this.invoiceEntity = builder.invoiceEntity;
    }

    public Integer getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public InvoiceEntity getInvoiceEntity() {
        return invoiceEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return number == that.number &&
                Objects.equals(id, that.id) &&
                Objects.equals(productEntity, that.productEntity) &&
                Objects.equals(invoiceEntity, that.invoiceEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, productEntity, invoiceEntity);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", number=" + number +
                ", productEntity=" + productEntity +
                ", invoiceEntity=" + invoiceEntity +
                '}';
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static final class OrderBuilder {
        private Integer id;
        private int number;
        private ProductEntity productEntity;
        private InvoiceEntity invoiceEntity;

        private OrderBuilder() {
        }

        public OrderBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public OrderBuilder withInvoiceEntity(InvoiceEntity invoiceEntity) {
            this.invoiceEntity = invoiceEntity;
            return this;
        }

        public OrderBuilder withNumber(int number) {
            this.number = number;
            return this;
        }

        public OrderBuilder withProductEntity(ProductEntity productEntity) {
            this.productEntity = productEntity;
            return this;
        }

        public OrderEntity build() {
            return new OrderEntity(this);
        }
    }
}
