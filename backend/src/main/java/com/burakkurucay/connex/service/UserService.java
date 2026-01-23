package com.burakkurucay.connex.service;

import com.burakkurucay.connex.entity.User;
import com.burakkurucay.connex.exception.user.UserNotFoundException;
import com.burakkurucay.connex.exception.user.UserAlreadyExistsException;
import com.burakkurucay.connex.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;

    // constructor
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /*
     *  Function to create new user
     * */
    public User createUser(String email, String password) {
        if (userRepo.existsByEmail(email)) {
            throw new UserAlreadyExistsException("email", email);
        }

        User user = new User(email, password);
        return userRepo.save(user);
    }

    /*
    *  Function to get user by email
    * */
    public User getUserByEmail(String email) {
        User user = userRepo.findByEmail(email);

        // check if the user with same email is exist
        if (user == null) {
            throw new UserNotFoundException("email", email);
        }

        return user;
    }
}
