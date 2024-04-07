package com.mycompany.product.service;

import com.mycompany.product.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User addUser(User user);
    void deleteUser(Long id);
    User updateUser(User user);
}
