package br.com.danielmota.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtUtil(
            @Value("${JWT_SECRET}") String secret,
            @Value("${JWT_EXPIRATION}") long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(UserDetails userDetails, Long userId) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(Map.of(
                        "userId", userId
                ))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        Object value = getClaims(token).get("userId");
        return value == null ? null : Long.valueOf(value.toString());
    }

    public String extractClaim(String token, String claim) {
        Object value = getClaims(token).get(claim);
        return value == null ? null : value.toString();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        return parser.parseSignedClaims(token).getPayload();
    }
}
