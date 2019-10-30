package ua.cashregister.model.domain;

import ua.cashregister.model.domain.enums.InvoiceStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Invoice implements Serializable {

    private Integer invoiceId;
    private Long invoiceCode;
    private String userName;
    private Boolean isPaid;
    private InvoiceStatus status;
    private Timestamp date;
    private String invoiceNotes;
    private Double cost = 0d;
    private Map<String, Product> products;


    public Invoice() {
        products = new HashMap<>();
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public Long getInvoiceCode() {
        return invoiceCode;
    }

    public String getUserName() {
        return userName;
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

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setInvoiceCode(Long invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setInvoiceNotes(String invoiceNotes) {
        this.invoiceNotes = invoiceNotes;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    @Deprecated
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
        sb.append(String.format("%n%-15s", "User name:")).append(userName);
        sb.append(String.format("%n%-15s", "Is payd:")).append(isPaid);
        sb.append(String.format("%n%-15s", "Order status:")).append(status);
        sb.append(String.format("%n%-15s", "Order date:")).append(date);
        sb.append(String.format("%n%-15s", "Order notes:")).append(invoiceNotes);
        sb.append(String.format("%n%12s", "Invoice:"));
        sb.append("\n************************************************************************************************");
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            num ++;
            String productCode = entry.getKey();
            Product product = entry.getValue();
            sb.append(String.format("%n%-4s",num)).append(String.format("%-8s", product.getCode()));
            sb.append(String.format("%-6s", product.getUomEn()));
        }
        sb.append("\n************************************************************************************************");
        sb.append("\n------------------------------------------------------------------------------------------------");
        return sb.toString();
    }
}
