package com.burakkurucay.connex.entity.profile.company;

import com.burakkurucay.connex.entity.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "company_profiles")
public class CompanyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Thw owner of the profile (user)
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

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

    public CompanyProfile(User user) {
        this.user = user;
    }

    /* getters & setters */

    public Long getId() { return id; }

    public User getUser() { return user; }

    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getIndustry() { return industry; }

    public void setIndustry(String industry) { this.industry = industry; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getWebsite() { return website; }

    public void setWebsite(String website) { this.website = website; }
}
