package com.burakkurucay.connex.entity.profile;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.burakkurucay.connex.entity.user.User;

@Entity
@Table(name = "personal_profiles")
public class PersonalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // profile owner (user)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "profile_description", columnDefinition = "TEXT")
    private String profileDescription;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(length = 255)
    private String location;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public PersonalProfile() {

    }

    public PersonalProfile(User user) {
        this.user = user;
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
    public Long getId() { return id; }

    public User getUser() { return user; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getProfileDescription() { return profileDescription; }

    public String getPhoneNumber() { return phoneNumber; }

    public String getLocation() { return location; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setProfileDescription(String description) { this.profileDescription = description; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setLocation(String location) { this.location = location; }
}

