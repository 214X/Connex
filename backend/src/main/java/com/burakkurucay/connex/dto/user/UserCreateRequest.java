package com.burakkurucay.connex.dto.user;

public class UserCreateRequest {
    private String email;
    private String password;

    // constructor
    public UserCreateRequest() {}

    // email getter and setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // password getter and setter
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
