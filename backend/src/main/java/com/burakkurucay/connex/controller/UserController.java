package com.burakkurucay.connex.controller;

import com.burakkurucay.connex.entity.User;
import com.burakkurucay.connex.service.UserService;
import com.burakkurucay.connex.dto.user.UserResponse;
import com.burakkurucay.connex.dto.user.UserCreateRequest;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // constructor
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser( @Valid @RequestBody UserCreateRequest req ) {
        User created = userService.createUser(req.getEmail(), req.getPassword());
        return toResponse(created);
    }

    @GetMapping("/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        User founded = userService.getUserByEmail(email);
        return toResponse(founded);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getCreatedAt());
    }
}
