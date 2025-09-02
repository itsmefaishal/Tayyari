package com.AuthServices.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {


    private static final String keyString = "your-secret-keyyour-secret-keyyour-secret-keyyour-secret-key";
    private static final SecretKey secretKey= Keys.hmacShaKeyFor(keyString.getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME = 86400000L;  // 1 day in milliseconds

    // Method to generate a JWT token
    public String doGenerateToken(Map<String,Object> claims , String userName) {
        return Jwts.builder()
                .subject(userName)// Set the subject (username)
                .issuedAt(new Date(System.currentTimeMillis()))  // Set the issue date
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Set expiration time
                .claims(claims)
                .signWith(secretKey)  // Sign the JWT with the secret key
                .compact();
    }
    //Method to generate token easily
    public String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",userDetails.getAuthorities());
        return doGenerateToken(claims,userDetails.getUsername());
    }

    // Method to extract username (subject) from JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Method to extract expiration date from JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Method to extract a specific claim from JWT
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Method to extract all claims from JWT (including username and expiration)
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Method to check if the token has expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Method to validate the token
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
