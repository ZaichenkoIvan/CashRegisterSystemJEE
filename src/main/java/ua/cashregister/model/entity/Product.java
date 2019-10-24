package ua.cashregister.model.entity;

public class Product {
    private final int code;
    private final String name;
    private final long price;
    private final boolean soldByWeight;
    private final Check check;
    private final int number;
    private final long weight;
    private final User manager;

    public Product(ProductBuilder builder) {
        this.code = builder.code;
        this.name = builder.name;
        this.price = builder.price;
        this.soldByWeight = builder.soldByWeight;
        this.check = builder.check;
        this.number = builder.number;
        this.weight = builder.weight;
        this.manager = builder.manager;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + code +
                ", name='" + name +
                ", price=" + price +
                ", soldByWeight=" + soldByWeight +
                ", number=" + number +
                ", weight=" + weight +
                '}';
    }

    public static final class ProductBuilder {
        private int code;
        private String name;
        private long price;
        private boolean soldByWeight;
        private Check check;
        private int number;
        private long weight;
        private User manager;

        private ProductBuilder() {
        }

        public static ProductBuilder aProduct() {
            return new ProductBuilder();
        }

        public ProductBuilder withCode(int code) {
            this.code = code;
            return this;
        }

        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withPrice(long price) {
            this.price = price;
            return this;
        }

        public ProductBuilder withSoldByWeight(boolean soldByWeight) {
            this.soldByWeight = soldByWeight;
            return this;
        }

        public ProductBuilder withCheck(Check check) {
            this.check = check;
            return this;
        }

        public ProductBuilder withNumber(int number) {
            this.number = number;
            return this;
        }

        public ProductBuilder withWeight(long weight) {
            this.weight = weight;
            return this;
        }

        public ProductBuilder withManager(User manager) {
            this.manager = manager;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
