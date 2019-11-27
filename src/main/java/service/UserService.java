package main.java.service;

import main.java.entity.User;

public interface UserService {
    User findUser(String login, String password);

    User registration(String userName, String login, String password);
}
