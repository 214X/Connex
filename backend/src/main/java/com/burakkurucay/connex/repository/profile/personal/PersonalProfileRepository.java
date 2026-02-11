package com.burakkurucay.connex.repository.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
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

    Optional<PersonalProfile> findByProfileUserId(Long userId);

    @Query("SELECT p FROM PersonalProfile p WHERE " +
            "LOWER(CONCAT(p.firstName, ' ', p.lastName)) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<PersonalProfile> searchProfiles(@Param("query") String query);
}
