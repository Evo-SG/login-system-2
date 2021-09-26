package com.loginsystem.loginsystem.controller;

import com.loginsystem.loginsystem.model.User;
import com.loginsystem.loginsystem.model.UserResponse;
import com.loginsystem.loginsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public List<User> index() {
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setMessage("Success");
        userResponse.setSuccess(true);
        return userResponse;
    }
}
