package project.model.dao;

import project.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudRepository<Integer, UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAll(int currentPage, int recordsPerPage);

    int getNumberOfRows();
}
