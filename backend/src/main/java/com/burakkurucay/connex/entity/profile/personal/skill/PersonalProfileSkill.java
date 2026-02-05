package com.burakkurucay.connex.entity.profile.personal.skill;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "personal_profile_skills")
public class PersonalProfileSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private PersonalProfile profile;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(columnDefinition = "integer default 0")
    private Integer level; // 0-10

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public PersonalProfileSkill() {
    }

    public PersonalProfileSkill(Long id, PersonalProfile profile, String name, String description, Integer level,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.description = description;
        this.level = level;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public static PersonalProfileSkillBuilder builder() {
        return new PersonalProfileSkillBuilder();
    }

    public static class PersonalProfileSkillBuilder {
        private Long id;
        private PersonalProfile profile;
        private String name;
        private String description;
        private Integer level;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        PersonalProfileSkillBuilder() {
        }

        public PersonalProfileSkillBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PersonalProfileSkillBuilder profile(PersonalProfile profile) {
            this.profile = profile;
            return this;
        }

        public PersonalProfileSkillBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonalProfileSkillBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PersonalProfileSkillBuilder level(Integer level) {
            this.level = level;
            return this;
        }

        public PersonalProfileSkillBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PersonalProfileSkillBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PersonalProfileSkill build() {
            return new PersonalProfileSkill(id, profile, name, description, level, createdAt, updatedAt);
        }
    }
}
