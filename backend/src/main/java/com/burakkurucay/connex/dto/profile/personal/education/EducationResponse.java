package com.burakkurucay.connex.dto.profile.personal.education;

import com.burakkurucay.connex.entity.profile.personal.education.PersonalProfileEducation;

import java.time.LocalDate;

public class EducationResponse {

    private Long id;
    private String schoolName;
    private String department;
    private LocalDate startDate;
    private LocalDate endDate;

    public static EducationResponse from(PersonalProfileEducation education) {
        EducationResponse response = new EducationResponse();
        response.id = education.getId();
        response.schoolName = education.getSchoolName();
        response.department = education.getDepartment();
        response.startDate = education.getStartDate();
        response.endDate = education.getEndDate();
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getDepartment() {
        return department;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
