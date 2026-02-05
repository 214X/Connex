package com.burakkurucay.connex.entity.profile.image;

import com.burakkurucay.connex.entity.profile.Profile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile_images", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "profile_id", "image_type" })
})
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Owner profile (aggregate root)
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_type", nullable = false)
    private ImageType imageType;

    /**
     * Absolute or relative file system path
     * Example: uploads/profiles/42/avatar/avatar.png
     */
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected ProfileImage() {
        // JPA
    }

    public ProfileImage(Profile profile, ImageType imageType, String filePath) {
        this.profile = profile;
        this.imageType = imageType;
        this.filePath = filePath;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters (controlled via service)

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
