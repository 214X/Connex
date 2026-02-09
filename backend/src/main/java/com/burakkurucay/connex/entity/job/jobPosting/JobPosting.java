package com.burakkurucay.connex.entity.job.jobPosting;

import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_postings")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* =========================
       RELATION
       ========================= */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_profile_id", nullable = false)
    private CompanyProfile companyProfile;

    /* =========================
       CORE JOB INFO
       ========================= */

    // İlan başlığı (örn: Junior Backend Developer)
    @Column(nullable = false)
    private String title;

    // Pozisyon / rol (Backend Developer, iOS Engineer vs.)
    @Column(nullable = false)
    private String position;

    // Uzun iş açıklaması
    @Column(nullable = false, length = 5000)
    private String description;

    // İşin konumu (Istanbul, Remote, Berlin vs.)
    @Column(nullable = false)
    private String location;

    /* =========================
       SKILLS
       ========================= */

    @ElementCollection
    @CollectionTable(
        name = "job_posting_skills",
        joinColumns = @JoinColumn(name = "job_posting_id")
    )
    @Column(name = "skill", nullable = false)
    private List<String> skills = new ArrayList<>();

    /* =========================
       JOB META
       ========================= */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkMode workMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    /* =========================
       APPLICATION INFO
       ========================= */

    // Başvuru sayısı (JobApplication üzerinden artırılır)
    @Column(nullable = false)
    private int applicationCount = 0;

    /* =========================
       TIME
       ========================= */

    private Instant publishedAt;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    /* =========================
       DOMAIN METHODS
       ========================= */

    public void publish() {
        this.status = JobStatus.PUBLISHED;
        this.publishedAt = Instant.now();
    }

    public void close() {
        this.status = JobStatus.CLOSED;
    }

    public void incrementApplicationCount() {
        this.applicationCount++;
    }

    /* =========================
       GETTERS
       ========================= */

    public Long getId() {
        return id;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }


    /* =========================
       SETTERS
       ========================= */

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
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

    /**
     * SADECE internal kullanım için:
     * create / onboarding aşamasında
     */
    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
