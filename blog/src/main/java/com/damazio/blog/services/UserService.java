package com.damazio.blog.services;

import com.damazio.blog.models.User;
import com.damazio.blog.repositories.UserRepository;
import com.damazio.blog.repositories.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método para criar um novo usuário
    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        return userRepository.save(user);
    }
}
