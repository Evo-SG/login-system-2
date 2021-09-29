package com.loginsystem.loginsystem.controller;

import com.loginsystem.loginsystem.model.AuthResponse;
import com.loginsystem.loginsystem.model.User;
import com.loginsystem.loginsystem.model.UserResponse;
import com.loginsystem.loginsystem.repository.UserRepository;
import com.loginsystem.loginsystem.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody User user) throws Exception {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        AuthResponse authResponse = new AuthResponse();

        if (foundUser.isPresent() && foundUser.get().getPassword().equals(user.getPassword())) {
            authResponse.setAccessToken(jwtUtil.generateToken(user));
            authResponse.setSuccess(true);
            if (foundUser.get().getUsername().equals("admin")) {
                authResponse.setRole("admin");
            } else {
                authResponse.setRole("user");
            }
            return new ResponseEntity<>(authResponse , HttpStatus.OK);
        } else {
            authResponse.setSuccess(false);
            return new ResponseEntity<>(authResponse , HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/")
    public List<User> index() {
        return userRepository.findAll();
    }
}
