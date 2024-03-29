package service;

import domain.User;

public interface UserService {
    User findUser(String login, String password);

    User registration(String userName, String login, String password);
}
