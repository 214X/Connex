package com.burakkurucay.connex.dto.onboarding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyOnboardingRequest {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Industry is required")
    @Size(min = 2, max = 50, message = "Industry must be between 2 and 50 characters")
    private String industry;

    @NotBlank(message = "Description is required")
    @Size(min = 20, max = 500, message = "Description must be between 20 and 500 characters")
    private String description;

    // Constructors
    public CompanyOnboardingRequest() {
    }

    public CompanyOnboardingRequest(String name, String industry, String description) {
        this.name = name;
        this.industry = industry;
        this.description = description;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
