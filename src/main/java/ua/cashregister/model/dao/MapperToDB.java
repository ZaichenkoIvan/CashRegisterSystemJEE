package ua.cashregister.model.dao;

@FunctionalInterface
public interface MapperToDB<E, P> {
    void map(E entity, P preparedStatement);
}
