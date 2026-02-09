package com.burakkurucay.connex.dto.job.jobPosting;

import com.burakkurucay.connex.entity.job.jobPosting.JobType;
import com.burakkurucay.connex.entity.job.jobPosting.WorkMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateJobPostingRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String position;

    @NotBlank
    @Size(max = 5000)
    private String description;

    @NotBlank
    private String location;

    @NotEmpty
    private List<@NotBlank String> skills;

    @NotNull
    private JobType jobType;

    @NotNull
    private WorkMode workMode;

    /* ===== GETTERS ===== */

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
}
