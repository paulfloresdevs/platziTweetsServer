package com.paulfloresdev.tweetsappbackend.controller;

import com.paulfloresdev.tweetsappbackend.DAO.User.UserDAO;
import com.paulfloresdev.tweetsappbackend.DAO.User.UserResponseDAO;
import com.paulfloresdev.tweetsappbackend.models.User;
import com.paulfloresdev.tweetsappbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // Register
    @PostMapping("/register")
    public ResponseEntity<UserResponseDAO> userRegistration(@RequestBody User user) {
        return this.service.registerUser(Optional.of(user));
    }

    // Login
    @PostMapping("/auth")
    public ResponseEntity<UserResponseDAO> userAuthentication(@RequestBody UserDAO user) {
        return this.service.authenticateUser(Optional.of(user));
    }

    // Get user by id
    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam Long id) {
        Optional<User> user = this.service.getUser(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get All users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.service.getAllUsers());
    }
}
