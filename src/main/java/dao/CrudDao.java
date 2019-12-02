package dao;

import java.util.Optional;

public interface CrudDao<T> {

    Optional<T> findById(Long id);

    Long insert(T item);

    void update(T item);
}
