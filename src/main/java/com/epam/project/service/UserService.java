package com.epam.project.service;

import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.UnknownUserRuntimeException;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    List<User> findUsersByRole(UserRole userRole) throws UnknownUserRuntimeException;

    User findUser(String name, String password) throws UnknownUserRuntimeException;

    boolean addUser(User user);

    boolean updateUser(User user);
}
