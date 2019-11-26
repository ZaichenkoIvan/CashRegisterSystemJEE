package main.java.dao;

import java.util.List;

public interface IDAO<T> {
    Long insert(T item);

    void update(T item);

    void delete(T item);

    List<T> findAll();

    public List<T> findAll(String where);
}
