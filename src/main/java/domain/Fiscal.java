package main.java.domain;

import java.util.Objects;

public class Fiscal {
    private Long id;
    private Double total;

    public Fiscal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fiscal fiscal = (Fiscal) o;
        return Objects.equals(id, fiscal.id) &&
                Objects.equals(total, fiscal.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, total);
    }

    @Override
    public String toString() {
        return "Fiscal{" +
                "id=" + id +
                ", total=" + total +
                '}';
    }
}
