package domain;

import java.util.Objects;

public class Checkspec {
    private Long id;
    private int canceled;
    private Long idCheck;
    private Long idGood;
    private int nds;
    private double ndstotal;
    private double price;
    private double quant;
    private double total;
    private int xcode;
    private String xname;
    private Check check;

    public Checkspec() {
    }

    public int getCanceled() {
        return this.canceled;
    }

    public void setCanceled(int canceled) {
        this.canceled = canceled;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCheck() {
        return this.idCheck;
    }

    public void setIdCheck(Long idCheck) {
        this.idCheck = idCheck;
    }

    public Long getIdGood() {
        return this.idGood;
    }

    public void setIdGood(Long idGood) {
        this.idGood = idGood;
    }

    public int getNds() {
        return this.nds;
    }

    public void setNds(int nds) {
        this.nds = nds;
    }

    public double getNdstotal() {
        return this.ndstotal;
    }

    public void setNdstotal(double ndstotal) {
        this.ndstotal = ndstotal;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuant() {
        return this.quant;
    }

    public void setQuant(double quant) {
        this.quant = quant;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Check getCheck() {
        return this.check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public int getXcode() {
        return xcode;
    }

    public void setXcode(int xcode) {
        this.xcode = xcode;
    }

    public String getXname() {
        return xname;
    }

    public void setXname(String xname) {
        this.xname = xname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Checkspec checkspec = (Checkspec) o;
        return canceled == checkspec.canceled &&
                nds == checkspec.nds &&
                Double.compare(checkspec.ndstotal, ndstotal) == 0 &&
                Double.compare(checkspec.price, price) == 0 &&
                Double.compare(checkspec.quant, quant) == 0 &&
                Double.compare(checkspec.total, total) == 0 &&
                xcode == checkspec.xcode &&
                Objects.equals(id, checkspec.id) &&
                Objects.equals(idCheck, checkspec.idCheck) &&
                Objects.equals(idGood, checkspec.idGood) &&
                Objects.equals(xname, checkspec.xname) &&
                Objects.equals(check, checkspec.check);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, canceled, idCheck, idGood, nds, ndstotal, price, quant, total, xcode, xname, check);
    }

    @Override
    public String toString() {
        return "Checkspec{" +
                "id=" + id +
                ", canceled=" + canceled +
                ", idCheck=" + idCheck +
                ", idGood=" + idGood +
                ", nds=" + nds +
                ", ndstotal=" + ndstotal +
                ", price=" + price +
                ", quant=" + quant +
                ", total=" + total +
                ", xcode=" + xcode +
                ", xname='" + xname + '\'' +
                ", check=" + check +
                '}';
    }
}
