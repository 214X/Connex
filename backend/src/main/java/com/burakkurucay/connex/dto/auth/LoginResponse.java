package com.burakkurucay.connex.dto.auth;

public class LoginResponse {

    private String accessToken;

    private String tokenType = "Bearer";

    private long expiresIn;

    // constructor
    public LoginResponse (String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    // getters
    public String getAccessToken() { return accessToken; }
    public long getExpiresIn() { return  expiresIn; }
}
