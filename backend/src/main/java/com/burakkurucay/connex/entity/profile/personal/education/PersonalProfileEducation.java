package com.burakkurucay.connex.entity.profile.personal.education;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile_educations")
public class PersonalProfileEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personal_profile_id", nullable = false)
    private PersonalProfile profile;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "department")
    private String department;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected PersonalProfileEducation() {
    }

    public PersonalProfileEducation(
            PersonalProfile profile,
            String schoolName,
            String department,
            LocalDate startDate,
            LocalDate endDate) {
        this.profile = profile;
        this.schoolName = schoolName;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters

    public Long getId() {
        return id;
    }

    public PersonalProfile getProfile() {
        return profile;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getDepartment() {
        return department;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
