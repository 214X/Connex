package com.burakkurucay.connex.dto.profile.company;

import com.burakkurucay.connex.entity.profile.company.CompanyProfile;

public class CompanyProfileResponse {

    private Long id;
    private Long userId;
    private String companyName;
    private String description;
    private String industry;
    private String location;
    private String website;

    public static CompanyProfileResponse from(CompanyProfile profile) {
        CompanyProfileResponse dto = new CompanyProfileResponse();
        dto.id = profile.getId();
        // Assuming CompanyProfile -> Profile -> User relationship
        if (profile.getProfile() != null && profile.getProfile().getUser() != null) {
            dto.userId = profile.getProfile().getUser().getId();
        }
        dto.companyName = profile.getCompanyName();
        dto.description = profile.getDescription();
        dto.industry = profile.getIndustry();
        dto.location = profile.getLocation();
        dto.website = profile.getWebsite();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
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
}
