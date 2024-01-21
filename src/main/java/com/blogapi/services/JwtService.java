package com.blogapi.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUsername(String jwt);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims,UserDetails userDetails);
}
