package com.burakkurucay.connex.entity.profile.company;

import com.burakkurucay.connex.entity.profile.Profile;
import jakarta.persistence.*;

/**
 * Company profile entity for business/organization users.
 * References Profile (aggregate root) via FK.
 */
@Entity
@Table(name = "company_profiles")
public class CompanyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to the aggregate root Profile.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false, unique = true)
    private Profile profile;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String industry;

    @Column(length = 255)
    private String location;

    @Column(length = 255)
    private String website;

    protected CompanyProfile() {
        // JPA
    }

    public CompanyProfile(Profile profile) {
        this.profile = profile;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    public String getIndustry() {
        return industry;
    }

    public String getLocation() {
        return location;
    }

    public String getWebsite() {
        return website;
    }

    // Setters

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
