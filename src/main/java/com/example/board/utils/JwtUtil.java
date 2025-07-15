package com.example.board.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.board.domain.enums.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final String secret = "your-secret-key"; // Replace with your actual secret key
    private final long expiration = 3600000; // 1 hour in milliseconds

    public String generateToken(String userName, Role role) {
        SecretKey key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject(userName)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
