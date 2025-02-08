package com.hospitalityhub.config;

import com.hospitalityhub.shared.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

/**
 * This class is used to generate and validate JWT token
 *
 * @author rimesh
 * @version 1.0
 */

@Service
public class JwtServiceImpl implements JwtService {
  SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expiration;

  @Override
  public String extractUserName(String token) {
    return extractClaims(token).getSubject();
  }

  /**
   * Generates a JWT (JSON Web Token) based on the provided UserDetails.
   *
   * @param userDetails The UserDetails containing information about the user.
   * @return The generated JWT.
   * @Author Subrat thapa
   */
  @Override
  public List<String> generateToken(UserDetails userDetails) {
    CustomUserDetails user = (CustomUserDetails) userDetails;
    System.out.println("what the hell"+user.getRole());
    return List.of(Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("role", user.getRole())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
            .signWith(key)
            .compact(),refreshToken(userDetails));
  }


  public String refreshToken(UserDetails userDetails) {
    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("type", "refreshtoken")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration * 2000))
            .signWith(key)
            .compact();
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    return userDetails.getUsername().equals(extractUserName(token)) && isTokenExpired(token);
  }

  /**
   * This Java code is part of a Spring Boot project and it's used to retrieve a token from either cookies or the Authorization header in an HTTP request.
   *
   * @param request
   * @return String
   * @author Subrat Thapa
   */
  public static String getToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(SecurityConstant.AUTH_TOKEN_HEADER)) {
          return cookie.getValue();
        }
      }
      String bearerToken = request.getHeader("Authorization");
      if (StringUtils.hasText(bearerToken)) {
        return bearerToken;
      }
    }
    return null;
  }

  /**
   * Checks if the expiration date of a given JWT (JSON Web Token) is in the future,
   * indicating that the token is not yet expired.
   *
   * @param token The JWT to check for expiration.
   * @return {@code true} if the token is not expired, {@code false} otherwise.
   */
  public boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().after(new Date());
  }

  /**
   * Extracts the claims (payload) from a given JWT (JSON Web Token).
   *
   * @param token The JWT from which to extract claims.
   * @return A Claims object representing the payload of the JWT.
   */
  private Claims extractClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
  }
}
