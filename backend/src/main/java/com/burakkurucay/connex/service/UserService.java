package com.burakkurucay.connex.service;

import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.entity.user.UserProfileType;
import com.burakkurucay.connex.exception.user.UserNotFoundException;
import com.burakkurucay.connex.exception.user.UserAlreadyExistsException;
import com.burakkurucay.connex.repository.UserRepository;
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
     *  Function to create new user
     * */
    public User createUser(String email,
                           String rawPassword,
                           UserProfileType type) {

        if (userRepo.existsByEmail(email)) {
            throw new UserAlreadyExistsException("email", email);
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(email, hashedPassword, type);
        return userRepo.save(user);
    }

    /*
     *  Function to get user by id
     * */
    public User getUserById(Long ID) {
        return userRepo.findById(ID)
            .orElseThrow(() -> new UserNotFoundException("ID", ID.toString()));
    }
}
