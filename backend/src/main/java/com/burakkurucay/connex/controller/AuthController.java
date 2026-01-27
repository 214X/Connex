package com.burakkurucay.connex.controller;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.user.UserRegisterRequest;
import com.burakkurucay.connex.dto.user.UserResponse;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.mapper.UserMapper;
import com.burakkurucay.connex.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ----- POST MAPPINGS -----
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> register(
            @Valid @RequestBody UserRegisterRequest req) {

        User registered = userService.register(req);
        UserResponse userResponse = UserMapper.toResponse(registered);
        return ApiResponse.success(userResponse);
    }
}
