package ua.cashregister.model.domain;

import java.util.Objects;

public class Product {

    private final Integer id;
    private final String code;
    private final Boolean isAvailable;
    private final String name;
    private final String description;
    private final Double cost;
    private final Double quantity;
    private final Double reservedQuantity = 0d;

    private Product(ProductBuilder builder) {
        this.id = builder.id;
        this.code = builder.code;
        this.isAvailable = builder.isAvailable;
        this.name = builder.name;
        this.description = builder.description;
        this.cost = builder.cost;
        this.quantity = builder.quantity;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getCost() {
        return cost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getReservedQuantity() {
        return reservedQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(code, product.code) &&
                Objects.equals(isAvailable, product.isAvailable) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(cost, product.cost) &&
                Objects.equals(quantity, product.quantity) &&
                Objects.equals(reservedQuantity, product.reservedQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, isAvailable, name, description, cost, quantity, reservedQuantity);
    }

    @Override
    public String toString() {
        return "\nProduct ID = " + id +
                "\nProduct code = " + code +
                "\nisAvailable: " + isAvailable +
                "\nname: " + name +
                "\ndescription: " + description +
                "\ncost: " + cost +
                "\nquantity: " + quantity +
                "\nreservedQuantity: " + reservedQuantity +
                "\n---------------------------------------------------------------------------------------------------";
    }

    public static final class ProductBuilder {
        private Integer id;
        private String code;
        private Boolean isAvailable;
        private String name;
        private String description;
        private Double cost;
        private Double quantity;
        private Double reservedQuantity = 0d;

        private ProductBuilder() {
        }

        public ProductBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ProductBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public ProductBuilder withIsAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withCost(Double cost) {
            this.cost = cost;
            return this;
        }

        public ProductBuilder withQuantity(Double quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductBuilder withReservedQuantity(Double reservedQuantity) {
            this.reservedQuantity = reservedQuantity;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
