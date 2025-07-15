package com.example.board.utils;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.board.domain.enums.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private final String secret = "your-secret-key"; // Replace with your actual secret key
    private final long expiration = 3600000; // 1 hour in milliseconds

    public String generateToken(String userName, Role role) {
        // Logic to generate JWT token using the secret and expiration time
        // This is a placeholder; implement your JWT generation logic here
        return Jwts.builder()
                .setSubject(userName)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }
}
