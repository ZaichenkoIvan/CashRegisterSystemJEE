package main.java.service.impl;

import main.java.dao.UserDao;
import main.java.dao.UserTypeDao;
import main.java.entity.User;
import main.java.exception.InvalidDataRuntimeException;
import main.java.service.UserService;
import org.apache.log4j.Logger;

import java.util.Objects;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final UserTypeDao userTypeDao;

    public UserServiceImpl(UserDao userDao, UserTypeDao userTypeDao) {
        this.userDao = userDao;
        this.userTypeDao = userTypeDao;
    }

    public User findUser(String login, String password) {
        if (Objects.isNull(login) || Objects.isNull(password)) {
            LOGGER.error("User data for finding is uncorrected");
            throw new InvalidDataRuntimeException("User data for finding is uncorrected");
        }

        return userDao.findUser(login, password);
    }

    public User registration(String userName, String login, String password) {
        if (Objects.isNull(login) || Objects.isNull(password)) {
            LOGGER.error("User data for registration is uncorrected");
            throw new InvalidDataRuntimeException("User data for registration is uncorrected");
        }

        if (userDao.findUserByLogin(login) != null) {
            return null;
        }

        User user = new User();
        user.setName(userName);
        user.setLogin(login);
        user.setPassword(password);
        user.setIdUserType(userTypeDao.findUserType("cashier"));
        userDao.insert(user);
        user.setPassword(null);

        return user;
    }
}
