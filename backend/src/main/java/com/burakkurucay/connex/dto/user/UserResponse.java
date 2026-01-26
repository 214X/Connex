package com.burakkurucay.connex.dto.user;

import com.burakkurucay.connex.entity.user.UserProfileType;

import java.time.LocalDateTime;

public class UserResponse {
    // data fields
    private long id;
    private String email;
    private UserProfileType profileType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // constructors
    public UserResponse() {}

    public UserResponse(Long id,
                        String email,
                        UserProfileType profileType,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.profileType = profileType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters
    public long getId() { return id; }
    public String getEmail() { return email; }
    public UserProfileType getProfileType() {return profileType; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
