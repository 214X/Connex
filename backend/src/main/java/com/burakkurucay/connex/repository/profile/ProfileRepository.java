package com.burakkurucay.connex.repository.profile;

import com.burakkurucay.connex.entity.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Profile aggregate root.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * Find profile by user ID.
     */
    Optional<Profile> findByUserId(Long userId);

    /**
     * Check if a profile exists for the given user ID.
     */
    boolean existsByUserId(Long userId);
}
