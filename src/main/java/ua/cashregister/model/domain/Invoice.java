package ua.cashregister.model.domain;

import ua.cashregister.model.domain.enums.InvoiceStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Invoice implements Serializable {

    private Integer invoiceId;
    private Long invoiceCode;
    private Integer userId;
    private Boolean isPaid;
    private InvoiceStatus status;
    private Timestamp date;
    private String invoiceNotes;
    private Double cost = 0d;
    private Map<String, Product> products;


    private Invoice(InvoiceBuilder builder) {
        this.invoiceId = builder.invoiceId;
        this.invoiceCode = builder.invoiceCode;
        this.userId = builder.userId;
        this.isPaid = builder.isPaid;
        this.status = builder.status;
        this.date = builder.date;
        this.invoiceNotes = builder.invoiceNotes;
        this.cost = builder.cost;
        this.products = builder.products;
    }

    public static InvoiceBuilder builder() {
        return new InvoiceBuilder();
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public Long getInvoiceCode() {
        return invoiceCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getInvoiceNotes() {
        return invoiceNotes;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public Double getCost() {
        return cost;
    }

    public void addProduct(String productCode, Product product) {
        products.put(productCode, product);
    }

    public void addProduct(Product product) {
        products.put(product.getCode(), product);
    }

    @Override
    public String toString() {
        int num = 0;
        StringBuilder sb = new StringBuilder(String.format("%n%-15s", "Order Id =")).append(invoiceId);
        sb.append(String.format("%n%-15s", "Order code:")).append(invoiceCode);
        sb.append(String.format("%n%-15s", "User id:")).append(userId);
        sb.append(String.format("%n%-15s", "Is payd:")).append(isPaid);
        sb.append(String.format("%n%-15s", "Order status:")).append(status);
        sb.append(String.format("%n%-15s", "Order date:")).append(date);
        sb.append(String.format("%n%-15s", "Order notes:")).append(invoiceNotes);
        sb.append(String.format("%n%12s", "Invoice:"));
        sb.append("\n************************************************************************************************");
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            num++;
            String productCode = entry.getKey();
            Product product = entry.getValue();
            sb.append(String.format("%n%-4s", num)).append(String.format("%-8s", product.getCode()));
            sb.append(String.format("%-6s", product.getName()));
        }
        sb.append("\n************************************************************************************************");
        sb.append("\n------------------------------------------------------------------------------------------------");
        return sb.toString();
    }

    public static final class InvoiceBuilder {
        private Integer invoiceId;
        private Long invoiceCode;
        private Integer userId;
        private Boolean isPaid;
        private InvoiceStatus status;
        private Timestamp date;
        private String invoiceNotes;
        private Double cost = 0d;
        private Map<String, Product> products;

        private InvoiceBuilder() {
        }

        public InvoiceBuilder withInvoiceId(Integer invoiceId) {
            this.invoiceId = invoiceId;
            return this;
        }

        public InvoiceBuilder withInvoiceCode(Long invoiceCode) {
            this.invoiceCode = invoiceCode;
            return this;
        }

        public InvoiceBuilder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public InvoiceBuilder withIsPaid(Boolean isPaid) {
            this.isPaid = isPaid;
            return this;
        }

        public InvoiceBuilder withStatus(InvoiceStatus status) {
            this.status = status;
            return this;
        }

        public InvoiceBuilder withDate(Timestamp date) {
            this.date = date;
            return this;
        }

        public InvoiceBuilder withInvoiceNotes(String invoiceNotes) {
            this.invoiceNotes = invoiceNotes;
            return this;
        }

        public InvoiceBuilder withCost(Double cost) {
            this.cost = cost;
            return this;
        }

        public InvoiceBuilder withProducts(Map<String, Product> products) {
            this.products = new HashMap<>(products);
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
