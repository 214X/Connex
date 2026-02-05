package com.burakkurucay.connex.repository.profile.company;

import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for CompanyProfile.
 * Queries via Profile FK.
 */
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {

    /**
     * Find company profile by profile ID.
     */
    Optional<CompanyProfile> findByProfileId(Long profileId);

    /**
     * Check if a company profile exists for the given profile ID.
     */
    boolean existsByProfileId(Long profileId);
}
