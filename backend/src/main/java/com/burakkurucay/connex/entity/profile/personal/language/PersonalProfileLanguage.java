package com.burakkurucay.connex.entity.profile.personal.language;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "personal_profile_languages")
public class PersonalProfileLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private PersonalProfile profile;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String level; // A1, A2, B1, B2, C1, C2, Native

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public PersonalProfileLanguage() {
    }

    public PersonalProfileLanguage(Long id, PersonalProfile profile, String language, String level,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.profile = profile;
        this.language = language;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
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

    public static PersonalProfileLanguageBuilder builder() {
        return new PersonalProfileLanguageBuilder();
    }

    public static class PersonalProfileLanguageBuilder {
        private Long id;
        private PersonalProfile profile;
        private String language;
        private String level;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        PersonalProfileLanguageBuilder() {
        }

        public PersonalProfileLanguageBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PersonalProfileLanguageBuilder profile(PersonalProfile profile) {
            this.profile = profile;
            return this;
        }

        public PersonalProfileLanguageBuilder language(String language) {
            this.language = language;
            return this;
        }

        public PersonalProfileLanguageBuilder level(String level) {
            this.level = level;
            return this;
        }

        public PersonalProfileLanguageBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PersonalProfileLanguageBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PersonalProfileLanguage build() {
            return new PersonalProfileLanguage(id, profile, language, level, createdAt, updatedAt);
        }
    }
}
