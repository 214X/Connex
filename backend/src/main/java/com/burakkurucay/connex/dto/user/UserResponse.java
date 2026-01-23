package com.burakkurucay.connex.dto.user;

import java.time.LocalDateTime;

public class UserResponse {
    // data fields
    private long id;
    private String email;
    private LocalDateTime createdAt;

    // constructors
    public UserResponse() {}

    public UserResponse(Long id, String email, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
    }

    // getters
    public long getId() { return id; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
