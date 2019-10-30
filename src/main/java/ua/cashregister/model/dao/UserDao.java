package ua.cashregister.model.dao;

import ua.cashregister.model.domain.User;
import ua.cashregister.model.domain.enums.UserRole;

import java.util.List;

public interface UserDao extends CrudDao<User, Integer> {

    List<User> findAllUsersInDB();

    List<User> findUsers();

    List<User> findUserByRole(UserRole role);

    User findUserByName(String name);
}
