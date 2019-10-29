package ua.cashregister.model.dao;

public interface CrudDao<E, ID> {

    boolean addToDB(E entity);

    E findById(ID id);

    boolean updateInDB(E user);

    boolean deleteFromDB(E user);
}
