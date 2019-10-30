package ua.cashregister.model.dao;

public interface CrudDao<E, ID> {

    boolean save(E entity);

    E findById(ID id);

    boolean update(E user);

    boolean deleteById(E user);
}
