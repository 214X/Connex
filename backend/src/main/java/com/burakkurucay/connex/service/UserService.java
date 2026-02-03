package com.burakkurucay.connex.service;

import com.burakkurucay.connex.dto.user.UserRegisterRequest;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.entity.user.UserStatus;
import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.exception.user.UserNotFoundException;
import com.burakkurucay.connex.exception.user.UserAlreadyExistsException;
import com.burakkurucay.connex.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    // constructor
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * Function to create new user
     */
    public User createUser(String email,
            String rawPassword,
            AccountType accountType,
            UserStatus status) {

        // normalize the email
        String normalizedEmail = email.toLowerCase().trim();

        // check if the email exists in db
        if (userRepo.existsByEmail(normalizedEmail)) {
            throw new UserAlreadyExistsException("email", normalizedEmail);
        }

        // hash the password
        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(normalizedEmail, hashedPassword, accountType, status);

        // try catch for race condition
        try {
            return userRepo.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistsException("email", normalizedEmail);
        }
    }

    /*
     * Function to get user by id
     */
    public User getUserById(Long ID) {
        return userRepo.findById(ID)
                .orElseThrow(() -> new UserNotFoundException("ID", ID.toString()));
    }

    public User register(UserRegisterRequest req) {

        // check password mismatching
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new BusinessException("Password and confirm password are not matching",
                    ErrorCode.PASSWORD_MISSMATCH);
        }

        return createUser(
                req.getEmail(),
                req.getPassword(),
                null,
                UserStatus.ONBOARDING);
    }

    public void completeUserOnboarding(AccountType type) {
        User user = getCurrentUser();
        user.setStatus(UserStatus.ACTIVE);
        user.setAccountType(type);
    }

    // TODO: errors
    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // if it is not authenticated
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new BusinessException(
                    "User is not authenticated",
                    ErrorCode.AUTH_UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        // JWT filter sets user id as principal
        if (principal == null) {
            throw new BusinessException(
                    "Invalid authentication context",
                    ErrorCode.AUTH_UNAUTHORIZED);
        }

        Long userId;
        try {
            userId = Long.valueOf(principal.toString());
        } catch (NumberFormatException ex) {
            throw new BusinessException(
                    "Invalid user identity in token",
                    ErrorCode.AUTH_UNAUTHORIZED);
        }

        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("ID", userId.toString()));
    }

    public void activateUser(Long userId) {
        User user = getUserById(userId);
        user.setStatus(UserStatus.ACTIVE);
        userRepo.save(user);
    }
}
