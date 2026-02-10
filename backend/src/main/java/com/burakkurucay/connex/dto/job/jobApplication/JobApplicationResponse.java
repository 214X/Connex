package com.burakkurucay.connex.dto.job.jobApplication;

import com.burakkurucay.connex.entity.job.jobApplication.ApplicationStatus;
import com.burakkurucay.connex.entity.job.jobApplication.JobApplication;

import java.time.Instant;

public class JobApplicationResponse {

    private Long id;
    private Long jobPostingId;
    private Long applicantProfileId;
    private ApplicationStatus status;
    private String message;
    private Instant createdAt;

    public static JobApplicationResponse from(JobApplication application) {
        JobApplicationResponse response = new JobApplicationResponse();
        response.id = application.getId();
        response.jobPostingId = application.getJobPosting().getId();
        response.applicantProfileId = application.getApplicant().getId();
        response.status = application.getStatus();
        response.message = application.getMessage();
        response.createdAt = application.getCreatedAt();
        return response;
    }

    public Long getId() {
        return id;
    }

    public Long getJobPostingId() {
        return jobPostingId;
    }

    public Long getApplicantProfileId() {
        return applicantProfileId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
