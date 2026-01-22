package com.burakkurucay.connex.controller;

import com.burakkurucay.connex.entity.User;
import com.burakkurucay.connex.service.UserService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // constructor
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public User createUser(
        @RequestParam String email,
        @RequestParam String password
    ) {
        return userService.createUser(email, password);
    }
}
