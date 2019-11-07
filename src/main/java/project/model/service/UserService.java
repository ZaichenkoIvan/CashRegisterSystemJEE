package project.model.service;

import project.model.domain.User;

import java.util.List;

public interface UserService {
    boolean register(User user);

    User login(String email, String password);

    List<User> findAll(int currentPage, int recordsPerPage);

    int getNumberOfRows();
}
