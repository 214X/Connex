package com.burakkurucay.connex.dto.job.jobApplication;

import java.util.List;

public class CompanyJobApplicationsResponse {

    private Long jobPostingId;
    private List<CompanyJobApplicationItem> applications;

    public CompanyJobApplicationsResponse(Long jobPostingId, List<CompanyJobApplicationItem> applications) {
        this.jobPostingId = jobPostingId;
        this.applications = applications;
    }

    public Long getJobPostingId() {
        return jobPostingId;
    }

    public List<CompanyJobApplicationItem> getApplications() {
        return applications;
    }
}
