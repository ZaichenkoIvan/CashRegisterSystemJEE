package entity;

import java.util.Objects;

public class FiscalEntity {
    private Long id;
    private Double total;

    public FiscalEntity() {
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
        FiscalEntity fiscal = (FiscalEntity) o;
        return Objects.equals(id, fiscal.id) &&
                Objects.equals(total, fiscal.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, total);
    }

    @Override
    public String toString() {
        return "FiscalEntity{" +
                "id=" + id +
                ", total=" + total +
                '}';
    }
}
