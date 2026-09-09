package com.nikhil.trello.service;

import com.nikhil.trello.dto.GeneratedToken;
import com.nikhil.trello.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {
    private final SecretKey secretKey;
    private final long expiration;

    public TokenService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ){
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public GeneratedToken generateAccessToken(User user){
        Instant now = Instant.now();
        Instant expiresAt = Instant.now().plusMillis(expiration);

        String accessToken = Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();

        return new GeneratedToken(accessToken, expiresAt);
    }

    public boolean isTokenValid(String token){
        try{
            extractClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    private Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token){
        return this.extractClaims(token).getSubject();
    }

    public UUID extractUserId(String token){
        String userId = extractClaims(token)
                .get("userId", String.class);

        return UUID.fromString(userId);
    }
}
