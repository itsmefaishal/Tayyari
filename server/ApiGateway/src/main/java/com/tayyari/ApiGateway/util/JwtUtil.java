package com.tayyari.ApiGateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

//    @Value("${jwt.secret}")
    //private String secret = "mysecretkey123mysecretkey123";

//    @Value("${jwt.expiration}")
    private Long expiration = 86400000L;

    private static final String keyString = "your-secret-keyyour-secret-keyyour-secret-keyyour-secret-key";
    private static final SecretKey secret= Keys.hmacShaKeyFor(keyString.getBytes(StandardCharsets.UTF_8));

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        List<Map<String, String>> roles =
                (List<Map<String, String>>) claims.get("roles");

        return roles.stream()
                .map(r -> r.get("authority"))
                .collect(Collectors.toList());
    }

    public List<String> extractRole(String token) {
        List<String> roles = extractRoles(token);
//        return roles.isEmpty() ? null : roles.get(0);
          return roles;
    }

//    public String extractRole(String token) {
//        Claims claims = extractAllClaims(token);
//        return (String) claims.get("role");
//    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
