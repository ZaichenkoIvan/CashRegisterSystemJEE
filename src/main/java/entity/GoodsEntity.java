package entity;

import java.util.Objects;

public class GoodsEntity {
    private Long id;
    private int code;
    private String name;
    private double quant;
    private double price;
    private String measure;
    private String comments;

    public GoodsEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return this.measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public double getQuant() {
        return this.quant;
    }

    public void setQuant(double quant) {
        this.quant = quant;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoodsEntity goods = (GoodsEntity) o;
        return code == goods.code &&
                Double.compare(goods.quant, quant) == 0 &&
                Double.compare(goods.price, price) == 0 &&
                Objects.equals(id, goods.id) &&
                Objects.equals(name, goods.name) &&
                Objects.equals(measure, goods.measure) &&
                Objects.equals(comments, goods.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, quant, price, measure, comments);
    }

    @Override
    public String toString() {
        return "GoodsEntity{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", quant=" + quant +
                ", price=" + price +
                ", measure='" + measure + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
