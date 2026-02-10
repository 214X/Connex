package com.burakkurucay.connex.controller.job;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.job.jobPosting.CreateJobPostingRequest;
import com.burakkurucay.connex.dto.job.jobPosting.JobPostingResponse;
import com.burakkurucay.connex.dto.job.jobPosting.UpdateJobPostingRequest;
import com.burakkurucay.connex.entity.job.jobPosting.JobPosting;
import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import com.burakkurucay.connex.mapper.job.JobPostingMapper;
import com.burakkurucay.connex.service.job.JobPostingService;
import com.burakkurucay.connex.service.profile.ProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobPostingController {

    private final JobPostingService jobPostingService;
    private final ProfileService profileService;

    public JobPostingController(
            JobPostingService jobPostingService,
            ProfileService profileService) {
        this.jobPostingService = jobPostingService;
        this.profileService = profileService;
    }

    /*
     * =========================
     * COMPANY – CREATE
     * =========================
     */

    @PostMapping
    public ApiResponse<JobPostingResponse> createJob(
            @Valid @RequestBody CreateJobPostingRequest request) {
        CompanyProfile companyProfile = profileService.getMyCompanyProfile();

        JobPosting jobPosting = JobPostingMapper.toEntity(request);
        JobPosting created = jobPostingService.create(jobPosting, companyProfile);

        return ApiResponse.success(
                JobPostingMapper.toResponse(created));
    }

    /*
     * =========================
     * COMPANY – UPDATE
     * =========================
     */

    @PutMapping("/{id}")
    public ApiResponse<JobPostingResponse> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody UpdateJobPostingRequest request) {
        CompanyProfile companyProfile = profileService.getMyCompanyProfile();

        JobPosting jobPosting = jobPostingService.getCompanyJobById(id, companyProfile);

        JobPostingMapper.updateEntity(jobPosting, request);

        return ApiResponse.success(
                JobPostingMapper.toResponse(jobPosting));
    }

    /*
     * =========================
     * COMPANY – PUBLISH
     * =========================
     */

    @PostMapping("/{id}/publish")
    public ApiResponse<Void> publishJob(@PathVariable Long id) {
        CompanyProfile companyProfile = profileService.getMyCompanyProfile();

        jobPostingService.publish(id, companyProfile);
        return ApiResponse.success();
    }

    /*
     * =========================
     * COMPANY – CLOSE
     * =========================
     */

    @PostMapping("/{id}/close")
    public ApiResponse<Void> closeJob(@PathVariable Long id) {
        CompanyProfile companyProfile = profileService.getMyCompanyProfile();

        jobPostingService.close(id, companyProfile);
        return ApiResponse.success();
    }

    /*
     * =========================
     * COMPANY – LIST OWN JOBS
     * =========================
     */

    @GetMapping("/my")
    public ApiResponse<List<JobPostingResponse>> getMyJobs() {
        CompanyProfile companyProfile = profileService.getMyCompanyProfile();

        List<JobPostingResponse> response = jobPostingService
                .getCompanyJobs(companyProfile)
                .stream()
                .map(JobPostingMapper::toResponse)
                .toList();

        return ApiResponse.success(response);
    }

    /*
     * =========================
     * PUBLIC – JOB FEED
     * =========================
     */

    @GetMapping
    public ApiResponse<List<JobPostingResponse>> getPublishedJobs() {
        List<JobPostingResponse> response = jobPostingService
                .getPublishedJobs()
                .stream()
                .map(JobPostingMapper::toResponse)
                .toList();

        return ApiResponse.success(response);
    }

    @GetMapping("/search")
    public ApiResponse<List<JobPostingResponse>> searchJobs(@RequestParam String q) {
        List<JobPostingResponse> response = jobPostingService
                .searchJobs(q)
                .stream()
                .map(JobPostingMapper::toResponse)
                .toList();

        return ApiResponse.success(response);
    }

    /*
     * =========================
     * PUBLIC – JOB DETAIL
     * =========================
     */

    @GetMapping("/{id}")
    public ApiResponse<JobPostingResponse> getPublishedJob(@PathVariable Long id) {
        JobPosting jobPosting = jobPostingService.getPublishedJobById(id);

        return ApiResponse.success(
                JobPostingMapper.toResponse(jobPosting));
    }

    /*
     * =========================
     * PUBLIC – COMPANY JOBS
     * =========================
     */

    @GetMapping("/company/{userId}")
    public ApiResponse<List<JobPostingResponse>> getCompanyPublicJobs(@PathVariable Long userId) {
        CompanyProfile companyProfile = profileService.getPublicCompanyProfileByUserId(userId);

        List<JobPostingResponse> response = jobPostingService
                .getCompanyPublishedJobs(companyProfile)
                .stream()
                .map(JobPostingMapper::toResponse)
                .toList();

        return ApiResponse.success(response);
    }

    /*
     * =========================
     * COMPANY – DELETE
     * =========================
     */

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteJob(@PathVariable Long id) {
        CompanyProfile companyProfile = profileService.getMyCompanyProfile();
        jobPostingService.delete(id, companyProfile);
        return ApiResponse.success();
    }
}
