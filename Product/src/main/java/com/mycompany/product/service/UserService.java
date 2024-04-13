package com.mycompany.product.service;

import com.mycompany.product.model.User;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User addUser(User user);
    void deleteUser(Long id);
    User updateUser(User user);
    Boolean ISAuth(HttpServletRequest request);
    User UserAuth(HttpServletRequest request);
    void ClearCookie(HttpServletResponse request);
}
