package com.burakkurucay.connex.controller;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.user.UserRegisterRequest;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.mapper.UserMapper;
import com.burakkurucay.connex.service.UserService;
import com.burakkurucay.connex.dto.user.UserResponse;
import com.burakkurucay.connex.dto.user.UserCreateRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ----- GET MAPPINGS -----
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponse userResponse = UserMapper.toResponse(user);
        return ApiResponse.success(userResponse);
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> me(
        @AuthenticationPrincipal Long userId
    ) {
        User user = userService.getUserById(userId);
        return ApiResponse.success(UserMapper.toResponse(user));
    }

    // ----- POST MAPPINGS -----
    // TODO: create user must not be public
    @PostMapping
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreateRequest req) {
        User created = userService.createUser(
                req.getEmail(),
                req.getPassword(),
                req.getProfileType(),
                req.isActive());
        UserResponse userResponse = UserMapper.toResponse(created);
        return ApiResponse.success(userResponse);
    }
}
