package main.java.dao;

import main.java.entity.UserType;

public interface UserTypeDao extends CrudDao<UserType> {

	Long findUserType(String type);
}
