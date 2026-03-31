package com.hcl.backend_template.user.auth.security;

import com.hcl.backend_template.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final int MIN_SECRET_LENGTH = 32;

  private final String secret;
  private final long expirationMs;
  private SecretKey signingKey;

  public JwtService(
      @Value("${app.jwt.secret:}") String secret,
      @Value("${app.jwt.expiration-ms:3600000}") long expirationMs) {
    this.secret = secret;
    this.expirationMs = expirationMs;
  }

  @PostConstruct
  void init() {
    if (secret == null || secret.length() < MIN_SECRET_LENGTH) {
      throw new IllegalStateException(
          "JWT secret must be at least " + MIN_SECRET_LENGTH + " characters");
    }
    this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(User user) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + expirationMs);
    return Jwts.builder()
        .subject(String.valueOf(user.getId()))
        .claim("email", user.getEmail())
        .claim("role", user.getRole().name())
        .issuedAt(now)
        .expiration(expiry)
        .signWith(signingKey)
        .compact();
  }

  public Claims parseToken(String token) {
    return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
  }
}
