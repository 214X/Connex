package com.burakkurucay.connex.dto.auth;

public class LoginResponse {

    private String token;

    private String tokenType = "Bearer";

    private long expiresIn;

    // constructor
    public LoginResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    // getters
    public String getToken() {
        return token;
    }

    public String getTokenType() { return tokenType; }

    public long getExpiresIn() {
        return expiresIn;
    }
}
