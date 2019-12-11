package dao;

import entity.UserTypeEntity;

public interface UserTypeDao extends CrudDao<UserTypeEntity> {
    Long findUserType(String type);
}
