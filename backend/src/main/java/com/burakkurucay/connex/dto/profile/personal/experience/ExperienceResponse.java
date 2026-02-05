package com.burakkurucay.connex.dto.profile.personal.experience;

import com.burakkurucay.connex.entity.profile.personal.experience.PersonalProfileExperience;

import java.time.LocalDate;

public class ExperienceResponse {

    private Long id;
    private String title;
    private String organization;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    public static ExperienceResponse from(PersonalProfileExperience experience) {
        ExperienceResponse response = new ExperienceResponse();
        response.id = experience.getId();
        response.title = experience.getTitle();
        response.organization = experience.getOrganization();
        response.startDate = experience.getStartDate();
        response.endDate = experience.getEndDate();
        response.description = experience.getDescription();
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOrganization() {
        return organization;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }
}
