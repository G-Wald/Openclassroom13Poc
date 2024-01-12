package com.poc.yourcaryourwarback.controllers;

import com.poc.yourcaryourwarback.models.User;
import com.poc.yourcaryourwarback.models.responses.LoginResponse;
import com.poc.yourcaryourwarback.models.requests.LoginRequest;
import com.poc.yourcaryourwarback.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

        @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
            var user = userService.findByUsernameOrEmail(loginRequest.getUsernameOrEmail());
            if(user == null || !user.getPassword().equals(loginRequest.getPassword())){
                return ResponseEntity.status(404).body(LoginResponse.builder().error("Username or password incorrect").build());
            }
            return  ResponseEntity.ok(LoginResponse.builder().email(user.getEmail()).username(user.getUsername()).build());
    }

}
