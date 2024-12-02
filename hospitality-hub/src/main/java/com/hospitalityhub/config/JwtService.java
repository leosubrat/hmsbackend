package com.hospitalityhub.config;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JwtService {
  String extractUserName(String token);

  List<String> generateToken(UserDetails userDetails);

  boolean isTokenValid(String token, UserDetails userDetails);
}
