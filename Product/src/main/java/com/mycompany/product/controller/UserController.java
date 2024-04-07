package com.mycompany.product.controller;

import com.mycompany.product.model.User;
import com.mycompany.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "list_users"; 
    }



    @PostMapping("/add")
    public String addUserSubmit(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/users/list"; 
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "edit_user"; 
        } else {
            return "redirect:/users/list"; 
        }
    }

    @PostMapping("/edit/{id}")
    public String editUserSubmit(@PathVariable Long id, @ModelAttribute User user) {
        user.setIdUser(id); 
        userService.updateUser(user);
        return "redirect:/users/list"; 
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users/list"; 
    }
}
