package com.burakkurucay.connex.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @Email(message = "Email must be valid email")
    @NotBlank(message = "Email must not be blank")
    @Size(max = 255, message = "Size of the email must not exceed 255 characters")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(max = 255, message = "Password is too long")
    private String password;


    // getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // setters
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
