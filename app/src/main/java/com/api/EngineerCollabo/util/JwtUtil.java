package com.api.EngineerCollabo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    @Value("${jwt.refresh.expiration}")
    private long REFRESH_EXPIRATION_TIME;

    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    public long getRefreshExpirationTime() {
        return REFRESH_EXPIRATION_TIME;
    }

    private Key getSigningKey() {
        // SECRET_KEYの長さが足りなければエラーを投げる
        if (SECRET_KEY.length() < 32) { // HS256の場合、256ビット(32バイト)が必要
            throw new IllegalArgumentException("JWT secret key must be at least 32 characters long.");
        }
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // アクセストークンの生成
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // リフレッシュトークンの生成
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .claim("type", "refresh")
                .compact();
    }

    // トークンからユーザー名を抽出
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // トークンの有効期限をチェック
    public boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    // アクセストークンの検証
    public boolean validateAccessToken(String token, String email) {
        final String extractedEmail = extractUsername(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // リフレッシュトークンの検証
    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.get("type", String.class).equals("refresh") 
                   && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

  private Claims parseClaims(String token) {
      try {
          return Jwts.parserBuilder()
                  .setSigningKey(getSigningKey())
                  .build()
                  .parseClaimsJws(token)
                  .getBody();
      } catch (JwtException e) {
          // JWTトークンの解析が失敗した場合に例外をスローする
          throw new IllegalStateException("Invalid JWT token", e);
      }
  }
}
