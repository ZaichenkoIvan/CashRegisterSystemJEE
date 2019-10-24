package ua.cashregister.model.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Shift {
    private final int id;
    private final Timestamp startTime;
    private final Timestamp endTime;
    private final User cashier;
    private final long startingCash;

    public Shift(ShiftBuilder builder) {
        this.id = builder.id;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.cashier = builder.cashier;
        this.startingCash = builder.startingCash;
    }

    public static ShiftBuilder builder() {
        return new ShiftBuilder();
    }

    public int getId() {
        return id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public User getCashier() {
        return cashier;
    }

    public long getStartingCash() {
        return startingCash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shift shift = (Shift) o;
        return id == shift.id &&
                startingCash == shift.startingCash &&
                Objects.equals(startTime, shift.startTime) &&
                Objects.equals(endTime, shift.endTime) &&
                Objects.equals(cashier, shift.cashier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, cashier, startingCash);
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", startTime='" + startTime +
                ", endTime='" + endTime +
                ", cashier=" + cashier +
                ", startingCash=" + startingCash +
                '}';
    }

    public static final class ShiftBuilder {
        private int id;
        private Timestamp startTime;
        private Timestamp endTime;
        private User cashier;
        private long startingCash;

        private ShiftBuilder() {
        }

        public static ShiftBuilder aShift() {
            return new ShiftBuilder();
        }

        public ShiftBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public ShiftBuilder withStartTime(Timestamp startTime) {
            this.startTime = startTime;
            return this;
        }

        public ShiftBuilder withEndTime(Timestamp endTime) {
            this.endTime = endTime;
            return this;
        }

        public ShiftBuilder withCashier(User cashier) {
            this.cashier = cashier;
            return this;
        }

        public ShiftBuilder withStartingCash(long startingCash) {
            this.startingCash = startingCash;
            return this;
        }

        public Shift build() {
            return new Shift(this);
        }
    }
}