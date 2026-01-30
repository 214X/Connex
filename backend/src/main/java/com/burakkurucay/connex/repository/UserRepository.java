package com.burakkurucay.connex.repository;

import com.burakkurucay.connex.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
    extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    // do not forget to use Optional<>
    Optional<User> findByEmail(String email);
}
