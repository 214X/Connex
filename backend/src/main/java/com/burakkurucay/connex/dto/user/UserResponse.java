package com.burakkurucay.connex.dto.user;

import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.entity.user.UserStatus;

import java.time.LocalDateTime;

public class UserResponse {
    // data fields
    private long id;
    private String email;
    private AccountType accountType;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // constructors
    public UserResponse() {
    }

    public UserResponse(Long id,
            String email,
            AccountType accountType,
            UserStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.accountType = accountType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters
    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public UserStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
