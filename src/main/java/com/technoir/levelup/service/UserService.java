package com.technoir.levelup.service;

import com.technoir.levelup.model.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    User findByEmail(String email);

    void markAsDeleted(Long id);

    void setNotActive(Long id);

    void delete(Long id);
}
