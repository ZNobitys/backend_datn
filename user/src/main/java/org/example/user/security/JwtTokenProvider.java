package org.example.user.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtTokenProvider {

    // Logger thủ công (không cần Lombok)
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private static final String JWT_SECRET = "MySuperSecretKeyThatIsAtLeast32Chars!";
    private static final long JWT_EXPIRATION_TIME = 864_000_000; // 10 ngày

    // Tạo key cho JWT
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Tạo token từ CustomUserDetails
    public String generateToken(CustomUserDetails customUserDetails) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION_TIME);

        List<String> roles = customUserDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority) // dùng method reference cho gọn
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(String.valueOf(customUserDetails.getId()))
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Lấy userId từ token
    public Integer getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Integer.parseInt(claims.getSubject());
        } catch (Exception ex) {
            log.error("Lỗi khi trích xuất Id từ JWT", ex);
            return null;
        }
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (SecurityException ex) {
            log.warn("Invalid JWT signature: ", ex);
        } catch (MalformedJwtException ex) {
            log.warn("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException ex) {
            log.warn("JWT token is expired: ", ex);
        } catch (UnsupportedJwtException ex) {
            log.warn("JWT token is unsupported: ", ex);
        } catch (IllegalArgumentException ex) {
            log.warn("JWT claims string is empty: ", ex);
        }
        return false;
    }
}
