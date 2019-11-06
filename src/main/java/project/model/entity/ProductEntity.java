package project.model.entity;

import java.util.Objects;

public class ProductEntity {
    private final Integer id;
    private final String code;
    private final String name;
    private final String description;
    private final int cost;
    private final int quantity;

    private ProductEntity(ProductBuilder builder) {
        this.id = builder.id;
        this.code = builder.code;
        this.name = builder.name;
        this.description = builder.description;
        this.cost = builder.cost;
        this.quantity = builder.quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, description, cost, quantity);
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", quantity=" + quantity +
                '}';
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }


    public static final class ProductBuilder {
        private Integer id;
        private String code;
        private String name;
        private String description;
        private int cost;
        private int quantity;

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

        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withCost(int cost) {
            this.cost = cost;
            return this;
        }

        public ProductBuilder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductEntity build() {
            return new ProductEntity(this);
        }
    }
}
