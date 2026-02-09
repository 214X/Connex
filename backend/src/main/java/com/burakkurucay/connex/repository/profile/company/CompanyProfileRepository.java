package com.burakkurucay.connex.repository.profile.company;

import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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
    /**
     * Check if a company profile exists for the given profile ID.
     */
    boolean existsByProfileId(Long profileId);

    /**
     * Search companies by name (case-insensitive).
     */
    List<CompanyProfile> findByCompanyNameContainingIgnoreCase(String companyName);

    /**
     * Fetch random company profiles.
     * Note: nativeQuery is used for RAND() which is specific to SQL dialects like
     * MySQL/PostgreSQL.
     */
    @Query(value = "SELECT * FROM company_profiles ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<CompanyProfile> findRandom(@Param("limit") int limit);
}
