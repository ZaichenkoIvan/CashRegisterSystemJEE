package main.java.dao;

import main.java.entity.UserTypeEntity;

public interface UserTypeDao extends CrudDao<UserTypeEntity> {
	Long findUserType(String type);
}
