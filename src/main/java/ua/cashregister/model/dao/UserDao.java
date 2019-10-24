package ua.cashregister.model.dao;

import ua.cashregister.model.entity.User;

import java.util.Optional;

public interface UserDao extends CrudDao<User, Long> {
    Optional<User> findByEmail(String email);

}
