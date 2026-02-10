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
    private Long applicantUserId;
    private String firstName;
    private String lastName;
    private String location;
    // can add avatarUrl here later if available
    private String responseNote;

    public static CompanyJobApplicationItem from(JobApplication application) {
        CompanyJobApplicationItem item = new CompanyJobApplicationItem();
        item.applicationId = application.getId();
        item.appliedAt = application.getCreatedAt();
        item.status = application.getStatus();
        item.message = application.getMessage();
        item.responseNote = application.getResponseNote();

        if (application.getApplicant() != null) {
            item.applicantProfileId = application.getApplicant().getId();
            item.applicantUserId = application.getApplicant().getProfile().getUser().getId();
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

    public void setApplicantProfileId(Long applicantProfileId) {
        this.applicantProfileId = applicantProfileId;
    }

    public Long getApplicantUserId() {
        return applicantUserId;
    }

    public void setApplicantUserId(Long applicantUserId) {
        this.applicantUserId = applicantUserId;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResponseNote() {
        return responseNote;
    }

    public void setResponseNote(String responseNote) {
        this.responseNote = responseNote;
    }
}
