package com.project.reservation.util;

import com.project.reservation.exception.BadRequestException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String USER_ID_KEY = "userId";
    private static final long EXPIRATION_TIME = 6 * 60 * 60 * 1000; // 6 hours
    private static final String INVALID_JWT_TOKEN_MESSAGE = "Invalid JWT token";

    @Value("${jwt.secret.key}")
    private String secretKey;

    public String generateToken(Long userId) {
        return Jwts.builder()
                .claim(USER_ID_KEY, userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(USER_ID_KEY, Long.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new BadRequestException(INVALID_JWT_TOKEN_MESSAGE);
        }
    }
}

