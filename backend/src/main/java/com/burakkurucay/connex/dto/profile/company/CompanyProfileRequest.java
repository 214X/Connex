package com.burakkurucay.connex.dto.profile.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class CompanyProfileRequest {

    @NotBlank(message = "Company name is required")
    @Size(max = 255)
    private String companyName;

    @Size(max = 5000)
    private String description;

    @Size(max = 255)
    private String industry;

    @Size(max = 255)
    private String location;

    @URL(message = "Website must be a valid URL")
    @Size(max = 255)
    private String website;

    public String getCompanyName() { return companyName; }
    public String getDescription() { return description; }
    public String getIndustry() { return industry; }
    public String getLocation() { return location; }
    public String getWebsite() { return website; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setDescription(String description) { this.description = description; }
    public void setIndustry(String industry) { this.industry = industry; }
    public void setLocation(String location) { this.location = location; }
    public void setWebsite(String website) { this.website = website; }
}
