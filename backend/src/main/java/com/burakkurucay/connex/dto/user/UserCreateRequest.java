package com.burakkurucay.connex.dto.user;

import com.burakkurucay.connex.entity.user.AccountType;
import jakarta.validation.constraints.*;

public class UserCreateRequest {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
        message = "Password must contain at least one letter and one number"
    )
    private String password;

    @NotNull(message = "Profile type must be declared")
    private AccountType accountType;

    @NotNull(message = "Activity of the user be declared")
    private boolean isActive;

    // constructor
    public UserCreateRequest() {}

    // email getter and setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // password getter and setter
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // profile type getter and setter
    public AccountType getProfileType() { return accountType; }
    public void setProfileType(AccountType profileType) { this.accountType = profileType; }

    // is active getter and setter
    public boolean isActive() { return isActive; }
    public void setActivity(boolean isActive) { this.isActive = isActive; }
}
