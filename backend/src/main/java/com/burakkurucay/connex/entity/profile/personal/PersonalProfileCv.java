package com.burakkurucay.connex.entity.profile.personal;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "personal_profile_cvs")
public class PersonalProfileCv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personal_profile_id", nullable = false, unique = true)
    private PersonalProfile personalProfile;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "stored_file_name", nullable = false, unique = true)
    private String storedFileName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    protected PersonalProfileCv() {
        // JPA
    }

    public PersonalProfileCv(PersonalProfile personalProfile, String originalFileName, String storedFileName,
            String contentType, Long fileSize, String storagePath) {
        this.personalProfile = personalProfile;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.storagePath = storagePath;
    }

    @PrePersist
    void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }

    // Getters

    public Long getId() {
        return id;
    }

    public PersonalProfile getPersonalProfile() {
        return personalProfile;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    // Setters for updating CV metadata if needed (e.g. replacing file)

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
