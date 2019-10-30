package ua.cashregister.model.dao;

@FunctionalInterface
public interface MapperFromDB<R, E> {
    E map(R resultSet);
}
