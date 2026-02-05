package com.burakkurucay.connex.dto.profile.personal.education;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CreatePersonalEducationRequest {

    @NotBlank(message = "School name is required")
    private String schoolName;

    private String department;

    private LocalDate startDate;

    private LocalDate endDate;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
