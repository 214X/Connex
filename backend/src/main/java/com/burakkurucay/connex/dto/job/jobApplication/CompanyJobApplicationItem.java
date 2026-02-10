package com.burakkurucay.connex.dto.job.jobApplication;

import com.burakkurucay.connex.entity.job.jobApplication.ApplicationStatus;
import com.burakkurucay.connex.entity.job.jobApplication.JobApplication;

import java.time.Instant;

public class CompanyJobApplicationItem {

    private Long applicationId;
    private Instant appliedAt;
    private ApplicationStatus status;
    private String message;

    // Applicant details
    private Long applicantProfileId;
    private String firstName;
    private String lastName;
    private String location;
    // can add avatarUrl here later if available

    public static CompanyJobApplicationItem from(JobApplication application) {
        CompanyJobApplicationItem item = new CompanyJobApplicationItem();
        item.applicationId = application.getId();
        item.appliedAt = application.getCreatedAt();
        item.status = application.getStatus();
        item.message = application.getMessage();

        if (application.getApplicant() != null) {
            item.applicantProfileId = application.getApplicant().getId();
            item.firstName = application.getApplicant().getFirstName();
            item.lastName = application.getApplicant().getLastName();
            item.location = application.getApplicant().getLocation();
        }

        return item;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public Instant getAppliedAt() {
        return appliedAt;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Long getApplicantProfileId() {
        return applicantProfileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocation() {
        return location;
    }
}
