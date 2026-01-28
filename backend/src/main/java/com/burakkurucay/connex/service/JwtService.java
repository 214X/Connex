package com.burakkurucay.connex.service;

import com.burakkurucay.connex.entity.user.User;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.time.Instant;

@Service
public class JwtService {

    private final String secretKey;

    private final long expirationSeconds;

    public JwtService(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expirationSeconds) {
        this.secretKey = secretKey;
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();

        return Jwts.builder()
                // identity for subject
                .setSubject(String.valueOf(user.getId()))
                // claims for the extra data for frontend
                .claim("email", user.getEmail())
                .claim(
                    "accountType",
                    user.getAccountType() != null
                        ? user.getAccountType().name()
                        : "UNDEFINED"
                )
                // set the creation time
                .setIssuedAt(Date.from(now))
                // set the expiration time
                .setExpiration(Date.from(now.plusSeconds(expirationSeconds)))
                // sign with the secret key
                .signWith(
                        Keys.hmacShaKeyFor(secretKey.getBytes()),
                        SignatureAlgorithm.HS256)
                // JSON → base64 → string
                .compact();
    }

    // getters
    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}
