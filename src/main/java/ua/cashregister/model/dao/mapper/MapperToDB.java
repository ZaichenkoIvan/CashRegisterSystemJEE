package ua.cashregister.model.dao.mapper;

@FunctionalInterface
public interface MapperToDB<E, P> {
    void map(E entity, P preparedStatement);
}
