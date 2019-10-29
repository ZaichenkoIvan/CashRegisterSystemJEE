package ua.cashregister.model.dao;

@FunctionalInterface
public interface Mapper<S, D> {
    void map(S source, D destination);
}
