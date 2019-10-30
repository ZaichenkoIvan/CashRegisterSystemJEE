package ua.cashregister.model.dao;

@FunctionalInterface
public interface Mapper<S, D> {
    S map(S source, D destination);
}
