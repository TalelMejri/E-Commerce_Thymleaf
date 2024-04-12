package com.mycompany.product.controller;


import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mycompany.product.config.SecurityConfig;
import com.mycompany.product.config.UserDetailsServiceImpl;
import com.mycompany.product.model.Category;
import com.mycompany.product.model.Credentials;
import com.mycompany.product.model.User;
import com.mycompany.product.repository.UserRepository;
import com.mycompany.product.service.CategoryServiceImpl;
import com.mycompany.product.service.UserService;

import net.minidev.json.JSONObject;

@Controller
public class AuthController {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	SecurityConfig security;
	@Autowired
    UserDetailsServiceImpl userservice;
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private CategoryServiceImpl categoryService;
	
	@Autowired
	UserService userServiceTest;
	
	 @GetMapping("/")
	 public String index() {
		  return "index"; 
	 }
	 
	 @GetMapping("/login")
	 public String LoginPage(Model model,@RequestParam(name = "message", defaultValue = "") String message) {
		  User user = new User();
		  model.addAttribute("user", user);
		  model.addAttribute("message", message);
		  return "login_page"; 
	 }
	 
	 @GetMapping("/register")
	 public String RegisterPage(Model model) {
		  User user = new User();
		  model.addAttribute("user", user);
		  return "register_page"; 
	 }
	 
	 @PostMapping("/RegisterUser")
	 public String RegisterUser(@ModelAttribute("user") User New_User) {
		  New_User.setRole("Client");
		  New_User.setPassword(security.passwordEncoder().encode(New_User.getPassword()));
		  userRepo.save(New_User);
		  return "login_page"; 
	 }
	 
		@PostMapping("/LoginUser")
		public String login(@ModelAttribute("user") Credentials auth,Model model,HttpServletResponse response){
			try {
				if(userRepo.findByEmail(auth.getEmail())==null) {
			         return "redirect:/login?message=User Not Found";
				}
				Authentication authsuser =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getEmail(),auth.getPassword()));
			}catch(Exception e) {
			    return "redirect:/login?message=Invalid Credentials";
			}
			UserDetails user_det=userservice.loadUserByUsername(auth.getEmail());
			User user=userRepo.getUserByemail(auth.getEmail());
		
			Cookie cookie=new Cookie("UserId",user.getIdUser().toString());
			cookie.setHttpOnly(false);
			cookie.setPath("/");
			cookie.setSecure(false);
			cookie.setMaxAge(-1);
			response.addCookie(cookie); 
			if(user.getRole().compareTo("Admin")==0) {
				return "redirect:categories/list_category";
			}else {
				return "redirect:/";
			}
			
		}
	
}
