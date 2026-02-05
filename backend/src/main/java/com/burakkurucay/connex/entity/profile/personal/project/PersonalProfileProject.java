package com.burakkurucay.connex.entity.profile.personal.project;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "personal_profile_projects")
public class PersonalProfileProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private PersonalProfile profile;

    @Column(nullable = false)
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String link;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PersonalProfileProject() {
    }

    public PersonalProfileProject(Long id, PersonalProfile profile, String name, String shortDescription,
            String description, String link, LocalDate startDate, LocalDate endDate, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalProfile getProfile() {
        return profile;
    }

    public void setProfile(PersonalProfile profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static PersonalProfileProjectBuilder builder() {
        return new PersonalProfileProjectBuilder();
    }

    public static class PersonalProfileProjectBuilder {
        private Long id;
        private PersonalProfile profile;
        private String name;
        private String shortDescription;
        private String description;
        private String link;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        PersonalProfileProjectBuilder() {
        }

        public PersonalProfileProjectBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PersonalProfileProjectBuilder profile(PersonalProfile profile) {
            this.profile = profile;
            return this;
        }

        public PersonalProfileProjectBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonalProfileProjectBuilder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public PersonalProfileProjectBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PersonalProfileProjectBuilder link(String link) {
            this.link = link;
            return this;
        }

        public PersonalProfileProjectBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public PersonalProfileProjectBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public PersonalProfileProjectBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PersonalProfileProjectBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PersonalProfileProject build() {
            return new PersonalProfileProject(id, profile, name, shortDescription, description, link, startDate,
                    endDate, createdAt, updatedAt);
        }
    }
}
