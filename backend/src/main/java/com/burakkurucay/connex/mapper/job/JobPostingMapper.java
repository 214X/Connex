package com.burakkurucay.connex.mapper.job;

import com.burakkurucay.connex.dto.job.jobPosting.CreateJobPostingRequest;
import com.burakkurucay.connex.dto.job.jobPosting.JobPostingResponse;
import com.burakkurucay.connex.dto.job.jobPosting.UpdateJobPostingRequest;
import com.burakkurucay.connex.entity.job.jobPosting.JobPosting;

public final class JobPostingMapper {

    private JobPostingMapper() {
        // utility class
    }

    /* =========================
       CREATE
       ========================= */

    public static JobPosting toEntity(CreateJobPostingRequest request) {
        JobPosting jobPosting = new JobPosting();

        jobPosting.setTitle(request.getTitle());
        jobPosting.setPosition(request.getPosition());
        jobPosting.setDescription(request.getDescription());
        jobPosting.setLocation(request.getLocation());
        jobPosting.setSkills(request.getSkills());
        jobPosting.setJobType(request.getJobType());
        jobPosting.setWorkMode(request.getWorkMode());

        return jobPosting;
    }

    /* =========================
       UPDATE
       ========================= */

    public static void updateEntity(
        JobPosting jobPosting,
        UpdateJobPostingRequest request
    ) {
        jobPosting.setTitle(request.getTitle());
        jobPosting.setPosition(request.getPosition());
        jobPosting.setDescription(request.getDescription());
        jobPosting.setLocation(request.getLocation());
        jobPosting.setSkills(request.getSkills());
    }

    /* =========================
       RESPONSE
       ========================= */

    public static JobPostingResponse toResponse(JobPosting jobPosting) {
        JobPostingResponse response = new JobPostingResponse();

        response.setId(jobPosting.getId());
        response.setTitle(jobPosting.getTitle());
        response.setPosition(jobPosting.getPosition());
        response.setDescription(jobPosting.getDescription());
        response.setLocation(jobPosting.getLocation());
        response.setSkills(jobPosting.getSkills());

        response.setJobType(jobPosting.getJobType());
        response.setWorkMode(jobPosting.getWorkMode());
        response.setStatus(jobPosting.getStatus());

        response.setApplicationCount(jobPosting.getApplicationCount());
        response.setPublishedAt(jobPosting.getPublishedAt());
        response.setCreatedAt(jobPosting.getCreatedAt());

        return response;
    }
}
