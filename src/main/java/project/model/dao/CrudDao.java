package project.model.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<ID, E> {
    boolean save(E entity);

    Optional<E> findById(ID id);

    List<E> findAll(int currentPage, int recordsPerPage);

    int getNumberOfRows();

    void update(E entity);

    boolean deleteById(ID id);
}
