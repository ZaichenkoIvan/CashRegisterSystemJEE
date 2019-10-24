package ua.cashregister.model.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CrudDao<E, ID> {
    E save(E entity);

    Optional<E> findById(ID id);

    List<E> findAll();

    void update(E entity);

    void deleteById(ID id);

    void deleteAllByIds(Set<ID> ids);
}
