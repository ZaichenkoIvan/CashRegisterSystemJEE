package com.epam.project.dao;

import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.DataNotFoundRuntimeException;

import java.util.List;

public interface UserDao {
    List<User> findAllUsersInDB() throws DataNotFoundRuntimeException;

    List<User> findUserByRole(UserRole role) throws DataNotFoundRuntimeException;

    User findUserByName(String name) throws DataNotFoundRuntimeException;

    boolean addUserToDB(User user);

    boolean updateUserInDB(User user);

    boolean deleteUserFromDB(User user);
}
