package com.cafe.Real.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component

public class JWTUtil {

	 @Value("${jwt.secret}")
	    private String secret;

	    @Value("${jwt.expiration}")
	    private Long expiration;

	    private Key getSigningKey() {
	        return Keys.hmacShaKeyFor(secret.getBytes());
	    }

	    public String generateToken(String email, String role, Long userId) {
	        Date now = new Date();
	        Date expiryDate = new Date(now.getTime() + expiration);

	        return Jwts.builder()
	                .setSubject(email)
	                .claim("role", role)
	                .claim("userId", userId)
	                .setIssuedAt(now)
	                .setExpiration(expiryDate)
	                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
	                .compact();
	    }

	    public String getEmailFromToken(String token) {
	        Claims claims = Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();

	        return claims.getSubject();
	    }

	    public boolean validateToken(String token) {
	        try {
	            Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token);
	            return true;
	        } catch (JwtException | IllegalArgumentException e) {
	            return false;
	        }
	    }
}
