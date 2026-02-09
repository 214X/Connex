package com.burakkurucay.connex.dto.job.jobPosting;

import com.burakkurucay.connex.entity.job.jobPosting.JobStatus;
import com.burakkurucay.connex.entity.job.jobPosting.JobType;
import com.burakkurucay.connex.entity.job.jobPosting.WorkMode;

import java.time.Instant;
import java.util.List;

public class JobPostingResponse {

    private Long id;
    private String title;
    private String position;
    private String description;
    private String location;
    private List<String> skills;

    private JobType jobType;
    private WorkMode workMode;
    private JobStatus status;

    private int applicationCount;
    private Instant publishedAt;
    private Instant createdAt;

    /* ===== GETTERS ===== */

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getSkills() {
        return skills;
    }

    public JobType getJobType() {
        return jobType;
    }

    public WorkMode getWorkMode() {
        return workMode;
    }

    public JobStatus getStatus() {
        return status;
    }

    public int getApplicationCount() {
        return applicationCount;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    /* ===== SETTERS ===== */

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public void setWorkMode(WorkMode workMode) {
        this.workMode = workMode;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public void setApplicationCount(int applicationCount) {
        this.applicationCount = applicationCount;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
