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

    @GetMapping("/authenticate")
    public AuthResponse authenticate(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(jwtUtil.generateToken(userDetails));
        authResponse.setSuccess(true);

        return authResponse;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/")
    public List<User> index() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> login(@RequestBody User user) {
        UserResponse userResponse = new UserResponse();
        System.out.print("hi");
        if (user.getUsername().equals("Admin")) {
            userResponse.setMessage("Success");
            userResponse.setSuccess(true);
            return new ResponseEntity<>(userResponse , HttpStatus.OK);
        } else {
            userResponse.setSuccess(false);
            userResponse.setMessage("FAILED");
            return new ResponseEntity<>(userResponse , HttpStatus.BAD_REQUEST);
        }
    }
}
