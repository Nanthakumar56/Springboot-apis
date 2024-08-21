package com.app.springboot_loginapp.service;

import com.app.springboot_loginapp.entity.User;
import com.app.springboot_loginapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
   
    public boolean loginUser(User user) {
        String userId = user.getUsername();
        User existingUser = userRepository.findById(userId).orElse(null);
        return existingUser != null && user.getPassword().equals(existingUser.getPassword());
    }

    public boolean isFirstTime(User user) {
        String userId = user.getUsername();
        User existingUser = userRepository.findById(userId).orElse(null);
        return existingUser != null && existingUser.getis_first_login();
    }
    public boolean updatePassword(User user) {
        if (user == null) {
            System.out.println("No user object provided.");
            return false;
        }

        String userId = user.getUsername();
        String email = user.getEmail();
        User existingUser = null;

        try {
            if (userId != null && !userId.isEmpty()) {
                existingUser = userRepository.findById(userId).orElse(null);
            } else if (email != null && !email.isEmpty()) {
                existingUser = userRepository.findByEmail(email);
            }

            if (existingUser != null) {
                System.out.println("Existing user found: " + existingUser.getUsername());
                System.out.println("Updating password for user: " + existingUser.getUsername());
                existingUser.setPassword(user.getPassword());
                existingUser.setis_first_login(false);
                userRepository.save(existingUser);
                return true;
            } else {
                System.out.println("No user found with provided username or email.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while updating the password: " + e.getMessage());
        }

        return false;
    }

}