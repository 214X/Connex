package com.burakkurucay.connex.controller.job;

import com.burakkurucay.connex.dto.job.jobApplication.ApplyJobRequest;
import com.burakkurucay.connex.dto.job.jobApplication.CompanyJobApplicationsResponse;
import com.burakkurucay.connex.dto.job.jobApplication.JobApplicationResponse;
import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.service.job.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    /*
     * =========================
     * PERSONAL ENDPOINTS
     * =========================
     */

    @PostMapping("/job-postings/{jobPostingId}/apply")
    public ApiResponse<JobApplicationResponse> applyToJob(@PathVariable Long jobPostingId,
            @Valid @RequestBody ApplyJobRequest request) {
        JobApplicationResponse response = jobApplicationService.applyToJobPosting(jobPostingId, request);
        return ApiResponse.success(response);
    }

    /*
     * =========================
     * COMPANY ENDPOINTS
     * =========================
     */

    @GetMapping("/company/job-postings/{jobPostingId}/applications")
    public ApiResponse<CompanyJobApplicationsResponse> getJobApplications(@PathVariable Long jobPostingId) {
        CompanyJobApplicationsResponse response = jobApplicationService.listApplicationsForCompany(jobPostingId);
        return ApiResponse.success(response);
    }

    @PutMapping("/company/applications/{applicationId}/status")
    public ApiResponse<Void> updateApplicationStatus(@PathVariable Long applicationId,
            @Valid @RequestBody com.burakkurucay.connex.dto.job.jobApplication.UpdateApplicationStatusRequest request) {
        jobApplicationService.updateApplicationStatus(applicationId, request);
        return ApiResponse.success();
    }
}
