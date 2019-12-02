package service.impl;

import dao.UserDao;
import dao.UserTypeDao;
import domain.User;
import entity.UserEntity;
import exception.ActionWithUserRuntimeException;
import exception.InvalidDataRuntimeException;
import service.UserService;
import service.encoder.EncoderPassword;
import service.mapper.UserMapper;
import service.validator.UserValidator;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final UserTypeDao userTypeDao;
    private final EncoderPassword encoderPassword;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    public UserServiceImpl(UserDao userDao, UserTypeDao userTypeDao, EncoderPassword encoderPassword,
                           UserValidator userValidator, UserMapper userMapper) {
        this.userDao = userDao;
        this.userTypeDao = userTypeDao;
        this.encoderPassword = encoderPassword;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
    }

    @Override
    public User findUser(String login, String password) {
        if (Objects.isNull(login) || Objects.isNull(password)) {
            LOGGER.warn("User data for finding is uncorrected");
            throw new InvalidDataRuntimeException("User data for finding is uncorrected");
        }

        String encodedPassword = encoderPassword.encode(password);
        Optional<UserEntity> userEntity = userDao.findUserByLogin(login);
        User user = userMapper.userEntityToUser(userEntity
                .orElseThrow(() -> new ActionWithUserRuntimeException("User not found")));

        if (!user.getPassword().equals(encodedPassword)) {
            LOGGER.warn("User with this login and password is not exist");
            throw new ActionWithUserRuntimeException("User with this login and password is not exist");
        }

        return user;
    }

    @Override
    public User registration(String userName, String login, String password) {
        if (Objects.isNull(login) || Objects.isNull(password)) {
            LOGGER.warn("User data for registration is uncorrected");
            throw new InvalidDataRuntimeException("User data for registration is uncorrected");
        }

        Optional<UserEntity> userByLogin = userDao.findUserByLogin(login);
        if (!userByLogin.isPresent()) {
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

        UserEntity userEntity = userMapper.userToUserEntity(user);
        userDao.insert(userEntity);

        return user;
    }
}