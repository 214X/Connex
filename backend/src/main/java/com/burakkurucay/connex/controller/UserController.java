package com.burakkurucay.connex.controller;

import com.burakkurucay.connex.dto.user.UserRegisterRequest;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.mapper.UserMapper;
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

    // ----- GET MAPPINGS -----
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return UserMapper.toResponse(user);
    }

    // ----- POST MAPPINGS -----
    // TODO: create user must not be public
    @PostMapping
    public UserResponse createUser( @Valid @RequestBody UserCreateRequest req ) {
        User created = userService.createUser(
            req.getEmail(),
            req.getPassword(),
            req.getProfileType(),
            req.isActive()
        );
        return UserMapper.toResponse(created);
    }
}
