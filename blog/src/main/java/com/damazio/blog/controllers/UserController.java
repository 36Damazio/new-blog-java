package com.damazio.blog.controllers;

import com.damazio.blog.models.User;
import com.damazio.blog.repositories.UserRequest;
import com.damazio.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

}
