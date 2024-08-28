package com.example.blockchain.controller;

import com.example.blockchain.entity.User;
import com.example.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user.getEmail(), user.getPassword());
    }

    @PostMapping("/updatePublicKey")
    public void registerAPublicKeyInUser(@RequestBody User user) {
        userService.updatePublicKey(user.getEmail(), user.getPublicKey());
    }

    @PostMapping("/login")
    public String getPublicKey(@RequestBody User user) {
        return userService.authenticateAndGetPublicKey(user.getEmail(), user.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid credentials or user not found."));
    }
}
