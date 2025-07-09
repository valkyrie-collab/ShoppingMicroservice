package com.valkyrie.authentication_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class WebTokenConfig {
    @Value("${jwts.security}")
    private String securityKey;

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private <Instance> Instance getClaim(String token, Function<Claims, Instance> claimsBearer) {
        Claims claims = getAllClaims(token);
        return claimsBearer.apply(claims);
    }

    private boolean isExpired(String token) {
        return !getClaim(token, Claims::getExpiration).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()  + 1000 * 60 * 60 * 30))
                .signWith(getKey()).compact();
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(getUsername(token)) && isExpired(token);
    }
}
