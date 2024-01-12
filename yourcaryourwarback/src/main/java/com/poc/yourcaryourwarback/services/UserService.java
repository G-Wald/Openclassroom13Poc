package com.poc.yourcaryourwarback.services;

import com.poc.yourcaryourwarback.models.User;
import com.poc.yourcaryourwarback.models.requests.LoginRequest;
import com.poc.yourcaryourwarback.models.responses.LoginResponse;
import com.poc.yourcaryourwarback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> userFromDb = userRepository.findByUsername(usernameOrEmail);
        if (userFromDb.isEmpty()) {
            userFromDb = userRepository.findByEmail(usernameOrEmail);
        }
        return userFromDb.orElse(null);
    }

    public LoginResponse authenticate(LoginRequest request) {

        var user = findByUsernameOrEmail(request.getUsernameOrEmail());

        if(user == null || !user.getPassword().equals(request.getPassword())){
            return LoginResponse.builder().error("Username or password incorrect").build();
        }

        return LoginResponse.builder().email(user.getEmail()).username(user.getUsername()).build();
    }

    public String getUsernameById(int id){

        Optional<User> user = userRepository.findById(id);

        return user.map(User::getUsername).orElse("");
    }
}
