/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.useraccount.user.util;

/**
 *
 * @author user
 */
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.*; // import the ArrayList class
 
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME_MS = 3600000; // 1 hour

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            // Invalid signature, token has been tampered with
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            // Handle any exceptions that might occur during token parsing
            return null;
        }
    }

    // Other methods...
}
