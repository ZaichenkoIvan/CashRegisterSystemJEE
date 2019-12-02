package main.java.dao;

import main.java.entity.UserEntity;

import java.util.Optional;

public interface UserDao extends CrudDao<UserEntity> {
    Optional<UserEntity> findUserByLogin(String login);
}
