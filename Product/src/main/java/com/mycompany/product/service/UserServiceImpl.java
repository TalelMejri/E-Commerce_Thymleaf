package com.mycompany.product.service;

import com.mycompany.product.model.User;
import com.mycompany.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public Boolean ISAuth(HttpServletRequest request) {
    	 String userIdString = null;
	        javax.servlet.http.Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (javax.servlet.http.Cookie cookie : cookies) {
	                if (cookie.getName().equals("UserId")) {
	                    userIdString = cookie.getValue();
	                    break;
	                }
	            }
	        }
	        if (userIdString == null) {
	            return false;
	        }else {
	        	return true;
	        }
    }
    
    @Override
    public User UserAuth(HttpServletRequest request) {
    	        String userIdString = null;
    	        javax.servlet.http.Cookie[] cookies = request.getCookies();
    	        if (cookies != null) {
    	            for (javax.servlet.http.Cookie cookie : cookies) {
    	                if (cookie.getName().equals("UserId")) {
    	                    userIdString = cookie.getValue();
    	                    break;
    	                }
    	            }
    	        }
    	        if (userIdString == null) {
    	            throw new IllegalArgumentException("UserId cookie not found");
    	        }
    	        Long userId = Long.parseLong(userIdString);
    	        return userRepository.findById(userId)
    	                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }
    
    @Override
    public void ClearCookie(HttpServletResponse response) {
    	 Cookie cookie = new Cookie("UserId", "");
         cookie.setPath("/");
         cookie.setMaxAge(3600);
         response.addCookie(cookie);
    }
}
