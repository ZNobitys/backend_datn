package org.example.user.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
    private static final String JWT_SECRET = "MySuperSecretKeyThatIsAtLeast32Chars!";
    private static final long JWT_EXPIRATION_TIME = 864_000_000;

    private Key getSigningKey() { return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));}

    public String generateToken(CustomUserDetails customUserDetails) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION_TIME);

        List<String> roles = customUserDetails.getAuthorities().stream().
                map(auth -> auth.getAuthority()).
                collect(Collectors.toList());
         return Jwts.builder()
                 .setSubject(String.valueOf(customUserDetails.getId()))
                 .claim("roles", roles)
                 .setIssuedAt(now)
                 .setExpiration(expirationDate)
                 .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                 .compact();
    }
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

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build().parseClaimsJws(token).getBody();
            return true;
        } catch (SecurityException ex) {
            log.warn("Invalid JWT signature: ", ex);
        } catch (MalformedJwtException ex) {
            log.warn("️Invalid JWT token: ", ex);
        } catch (ExpiredJwtException ex) {
            log.warn("️JWT token is expired: ", ex);
        } catch (UnsupportedJwtException ex) {
            log.warn("️JWT token is unsupported: ", ex);
        } catch (IllegalArgumentException ex) {
            log.warn("JWT claims string is empty: ", ex);
        }
        return false;
    }
}
