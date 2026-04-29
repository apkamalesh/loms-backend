package com.loms.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${loms.jwt.secret}") private String jwtSecret;
    @Value("${loms.jwt.expiration}") private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(Authentication authentication) {
        UserDetails u = (UserDetails) authentication.getPrincipal();
        return Jwts.builder().subject(u.getUsername())
            .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+jwtExpirationMs))
            .signWith(getSigningKey()).compact();
    }
    public String getUserEmailFromToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public boolean validateToken(String token) {
        try { Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token); return true; }
        catch (Exception e) { return false; }
    }
}
