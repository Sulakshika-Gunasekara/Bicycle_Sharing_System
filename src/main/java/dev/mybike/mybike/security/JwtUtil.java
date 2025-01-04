package dev.mybike.mybike.security;

import dev.mybike.mybike.model.Role;
import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.repository.RiderRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final SecretKey secretKey = Keys.hmacShaKeyFor(
            "my-super-secret-key-my-super-secret-key-my-super-secret-key-my-key-123".getBytes());

    // Expiration - 1 day
    private final int jwtExpirationMs = 86400000;
    private final RiderRepository userRepository;

    public JwtUtil(RiderRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Generate Token
    public String generateToken(String username) {
        Optional<Rider> user = userRepository.findByRidername(username);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found: " + username);
        }

        Set<Role> roles = user.get().getRoles();

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles.stream()
                        .map(Role::getname)
                        .collect(Collectors.joining(",")))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // Extract Username
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract Roles
    public Set<String> extractRoles(String token) {
        String rolesString = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", String.class);

        return Set.of(rolesString.split(","));
    }

    // Validate Token
    public boolean isTokenVaild(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Object jwtExpirationMs() {
        return jwtExpirationMs;
    }
}
