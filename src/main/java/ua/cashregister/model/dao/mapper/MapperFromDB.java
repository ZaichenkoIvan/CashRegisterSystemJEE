package ua.cashregister.model.dao.mapper;

@FunctionalInterface
public interface MapperFromDB<R, E> {
    E map(R resultSet);
}
