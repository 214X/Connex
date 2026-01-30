package com.burakkurucay.connex.dto.profile.company;

import com.burakkurucay.connex.entity.profile.CompanyProfile;

public class CompanyProfileResponse {

    private Long id;
    private String companyName;
    private String description;
    private String industry;
    private String location;
    private String website;

    public static CompanyProfileResponse from(CompanyProfile profile) {
        CompanyProfileResponse dto = new CompanyProfileResponse();
        dto.id = profile.getId();
        dto.companyName = profile.getCompanyName();
        dto.description = profile.getDescription();
        dto.industry = profile.getIndustry();
        dto.location = profile.getLocation();
        dto.website = profile.getWebsite();
        return dto;
    }

    public Long getId() { return id; }
    public String getCompanyName() { return companyName; }
    public String getDescription() { return description; }
    public String getIndustry() { return industry; }
    public String getLocation() { return location; }
    public String getWebsite() { return website; }
}
