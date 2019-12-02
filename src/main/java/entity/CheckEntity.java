package entity;

import java.sql.Date;
import java.util.Objects;

public class CheckEntity {
    private Long id;
    private int canceled;
    private Long creator;
    private Date crtime;
    private double discount;
    private double total;
    private Integer registration;

    public CheckEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCanceled() {
        return this.canceled;
    }

    public void setCanceled(int canceled) {
        this.canceled = canceled;
    }

    public Long getCreator() {
        return this.creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCrtime() {
        return this.crtime;
    }

    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getRegistration() {
        return registration;
    }

    public void setRegistration(Integer registration) {
        this.registration = registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CheckEntity check = (CheckEntity) o;
        return canceled == check.canceled &&
                Double.compare(check.discount, discount) == 0 &&
                Double.compare(check.total, total) == 0 &&
                Objects.equals(id, check.id) &&
                Objects.equals(creator, check.creator) &&
                Objects.equals(crtime, check.crtime) &&
                Objects.equals(registration, check.registration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, canceled, creator, crtime, discount, total, registration);
    }

    @Override
    public String toString() {
        return "CheckEntity{" +
                "id=" + id +
                ", canceled=" + canceled +
                ", creator=" + creator +
                ", crtime=" + crtime +
                ", discount=" + discount +
                ", total=" + total +
                ", registration=" + registration +
                '}';
    }
}
