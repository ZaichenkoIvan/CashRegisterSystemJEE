package main.java.dao;

public interface IDAO<T> {
    Long insert(T item);

    void update(T item);

    void delete(T item);
}
