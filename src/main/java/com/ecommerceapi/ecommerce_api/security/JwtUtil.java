package com.ecommerceapi.ecommerce_api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JwtUtil.class);

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate token
    public String generateToken(String email, String role) {
        logger.info("Generating token for user [{}] with role [{}]", email, role);

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h expiry
                .signWith(key)
                .compact();
    }

    // Extract email
    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract role
    public String extractRole(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}
