package com.mycompany.product.controller;

import com.mycompany.product.model.User;
import com.mycompany.product.repository.UserRepository;
import com.mycompany.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;;
    
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users =userRepo.getUsers();
        model.addAttribute("users", users);
        return "admin/Users/List_Users"; 
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

    @GetMapping("/delete")
    public String deleteUser(Long id) {
        userService.deleteUser(id);
        return "admin/Users/List_Users";  
    }
}
