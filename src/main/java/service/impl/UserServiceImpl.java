package main.java.service.impl;

import main.java.dao.UserDao;
import main.java.dao.UserTypeDao;
import main.java.entity.User;
import main.java.exception.InvalidDataRuntimeException;
import main.java.exception.UserNotExistRuntimeException;
import main.java.service.UserService;
import main.java.service.encoder.EncoderPassword;
import main.java.service.validator.UserValidator;
import org.apache.log4j.Logger;

import java.util.Objects;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final UserTypeDao userTypeDao;
    private final EncoderPassword encoderPassword;
    private final UserValidator userValidator;

    public UserServiceImpl(UserDao userDao, UserTypeDao userTypeDao, EncoderPassword encoderPassword,
                           UserValidator userValidator) {
        this.userDao = userDao;
        this.userTypeDao = userTypeDao;
        this.encoderPassword = encoderPassword;
        this.userValidator = userValidator;
    }

    @Override
    public User findUser(String login, String password) {
        if (Objects.isNull(login) || Objects.isNull(password)) {
            LOGGER.error("User data for finding is uncorrected");
            throw new InvalidDataRuntimeException("User data for finding is uncorrected");
        }

        String encodedPassword = encoderPassword.encode(password);
        User user = userDao.findUserByLogin(login);

        if (!user.getPassword().equals(encodedPassword)) {
            LOGGER.error("User with this login and password is not exist");
            throw new UserNotExistRuntimeException("User with this login and password is not exist");
        }

        return user;
    }

    @Override
    public User registration(String userName, String login, String password) {
        if (Objects.isNull(login) || Objects.isNull(password)) {
            LOGGER.error("User data for registration is uncorrected");
            throw new InvalidDataRuntimeException("User data for registration is uncorrected");
        }

        if (userDao.findUserByLogin(login) != null) {
            return null;
        }

        String encodedPassword = encoderPassword.encode(password);
        User user = new User();
        user.setName(userName);
        user.setLogin(login);
        user.setPassword(password);
        userValidator.validate(user);

        user.setPassword(encodedPassword);
        user.setIdUserType(userTypeDao.findUserType("cashier"));
        userDao.insert(user);

        return user;
    }
}