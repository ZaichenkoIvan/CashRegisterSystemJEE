package main.java.dao;

import main.java.entity.User;

public interface UserDao extends CrudDao<User> {
    User findUserByLogin(String login);
}
