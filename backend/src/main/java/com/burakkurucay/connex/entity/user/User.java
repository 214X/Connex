package com.burakkurucay.connex.entity.user;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.burakkurucay.connex.entity.user.UserProfileType;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name="profile_type", nullable = false)
    private UserProfileType profileType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // empty constructor for Hibernate
    protected User() {}

    public User(String email, String password, UserProfileType profileType) {
        this.email = email;
        this.password = password;
        this.profileType = profileType;
    }

    // pre businesses
    @PrePersist
    public void onCreate() {
        isActive = true;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserProfileType getProfileType() { return profileType; }

    public boolean isActive() { return isActive; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
