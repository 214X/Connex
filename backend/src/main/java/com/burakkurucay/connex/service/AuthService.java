package com.burakkurucay.connex.service;

import com.burakkurucay.connex.dto.auth.LoginRequest;
import com.burakkurucay.connex.dto.auth.LoginResponse;
import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.exception.user.UserNotFoundException;
import com.burakkurucay.connex.repository.UserRepository;
import com.burakkurucay.connex.exception.common.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    UserRepository userRepo;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;

    public AuthService(
        UserRepository userRepo,
        JwtService jwtService,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }


    public LoginResponse login(LoginRequest loginReq) {
        // check if the email exists
        if (!userRepo.existsByEmail(loginReq.getEmail())) {
            throw new UserNotFoundException("email", loginReq.getEmail());
        }

        // get the user data
        // TODO: exception
        User usr = userRepo.findByEmail(loginReq.getEmail())
            .orElseThrow(() -> new UserNotFoundException("email", loginReq.getEmail()));

        // check if the password is correct
        // TODO: exception
        if (!passwordEncoder.matches(loginReq.getPassword(), usr.getPassword())) {
            throw new BusinessException(
                "AUTH_INVALID_CREDENTIALS"
            );
        }

        // generate a new token
        String token = jwtService.generateToken(usr);

        return new LoginResponse(token, jwtService.getExpirationSeconds());
    }
}
