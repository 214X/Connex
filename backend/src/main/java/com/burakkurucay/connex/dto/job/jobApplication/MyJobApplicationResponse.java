package com.burakkurucay.connex.dto.job.jobApplication;

import com.burakkurucay.connex.entity.job.jobApplication.JobApplication;
import com.burakkurucay.connex.entity.job.jobApplication.ApplicationStatus;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyJobApplicationResponse {

    private Long id;
    private Long jobPostingId;
    private String jobTitle;
    private String companyName;
    private String companyLogoUrl; // Optional, strict for UI
    private ApplicationStatus status;

    @JsonProperty("responseNote")
    private String responseNote;
    private Instant appliedAt;

    public static MyJobApplicationResponse from(JobApplication application) {
        MyJobApplicationResponse response = new MyJobApplicationResponse();
        response.id = application.getId();
        response.jobPostingId = application.getJobPosting().getId();
        response.jobTitle = application.getJobPosting().getTitle();
        response.companyName = application.getJobPosting().getCompanyProfile().getCompanyName();
        // companyLogoUrl can be added if available on JobPosting or CompanyProfile
        response.status = application.getStatus();
        response.responseNote = application.getResponseNote();
        response.appliedAt = application.getCreatedAt();
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobPostingId() {
        return jobPostingId;
    }

    public void setJobPostingId(Long jobPostingId) {
        this.jobPostingId = jobPostingId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public void setCompanyLogoUrl(String companyLogoUrl) {
        this.companyLogoUrl = companyLogoUrl;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Instant getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Instant appliedAt) {
        this.appliedAt = appliedAt;
    }
}
