package com.burakkurucay.connex.repository.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository for PersonalProfile.
 * Queries via Profile FK.
 */
public interface PersonalProfileRepository extends JpaRepository<PersonalProfile, Long> {

    /**
     * Find personal profile by profile ID.
     */
    Optional<PersonalProfile> findByProfileId(Long profileId);

    /**
     * Check if a personal profile exists for the given profile ID.
     */
    boolean existsByProfileId(Long profileId);
}
