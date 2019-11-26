package main.java.dao;

public interface IUserTypeDAO<T> extends IDAO<T> {

	Long findUserType(String type);
}
