package main.java.dao;

import main.java.entity.User;

public interface UserDao extends CrudDao<User> {

    User findUser(String login, String password);

    User findUserByLogin(String login);
}
