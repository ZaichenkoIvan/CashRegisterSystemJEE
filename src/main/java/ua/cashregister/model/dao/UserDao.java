package ua.cashregister.model.dao;

import ua.cashregister.model.domain.User;
import ua.cashregister.model.domain.UserRole;

import java.util.List;

public interface UserDao extends CrudDao<User, Integer> {

    Integer calculateUsersNumber();

    List<User> findAllUsersInDB();

    List<User> findUsers(Integer first, Integer offset);

    List<User> findUserByRole(UserRole role);

    User findUserByName(String name);
}
