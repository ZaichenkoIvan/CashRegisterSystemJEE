package com.epam.project.service.implementation;

import com.epam.project.dao.UserDao;
import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.DataNotFoundRuntimeException;
import com.epam.project.exceptions.UnknownUserRuntimeException;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUser(String name, String password) throws UnknownUserRuntimeException {
        try {
            User user = userDao.findUserByName(name);
            if (!user.getPassword().equals(password))
                throw new UnknownUserRuntimeException();
            return user;
        } catch (DataNotFoundRuntimeException ex) {
            LOGGER.error(ex);
            throw new UnknownUserRuntimeException();
        }
    }

    //TODO:rename
    @Override
    public List<User> findAllUsers() throws UnknownUserRuntimeException {
        return userDao.findAllUsersInDB();
    }


    @Override
    public List<User> findUsersByRole(UserRole userRole) throws UnknownUserRuntimeException {
        List<User> users;
        users = userDao.findUserByRole(userRole);
        return users;
    }

    @Override
    public boolean addUser(User user) {
        return userDao.addUserToDB(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUserInDB(user);
    }

}
