package main.java.dao;

import main.java.entity.User;

public interface IUserDAO<T> extends IDAO<T> {

    User findUser(String login, String password);

    User findUserByLogin(String login);
}
