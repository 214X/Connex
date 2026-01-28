package com.burakkurucay.connex.service;

import com.burakkurucay.connex.entity.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final Key signingKey;

    private final long expirationSeconds;

    // constructor
    public JwtService(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expirationSeconds) {

        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); // ? kaynak nerede
        this.expirationSeconds = expirationSeconds;
    }

    // getters
    public long getExpirationSeconds() { return expirationSeconds; }

    /*
    * Method to generate new login token
    * */
    public String generateToken(User user) {
        Instant now = Instant.now();

        return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .claim("email", user.getEmail())
            .claim("accountType",
                user.getAccountType() != null ? user.getAccountType().name() : "UNDEFINED"
            )
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusSeconds(expirationSeconds)))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact();
    }

    /*
     * Method to check if the input token is valid
     * */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date exp = claims.getExpiration();
            return exp != null && exp.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // signature invalid, malformed, expired, vs.
            return false;
        }
    }

    // TOKEN EXTRACTORS
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public Long extractUserId(String token) {
        String sub = extractAllClaims(token).getSubject();
        return Long.parseLong(sub);
    }
}
