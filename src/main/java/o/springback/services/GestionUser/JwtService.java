package o.springback.services.GestionUser;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    // Replace this with a secure, unpredictable key. Load it securely in a real application (e.g., from env variables).
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    // Generate token with given user name
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    // Create a JWT token with specified claims and subject (user name)
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date()) // Token issued at current time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30 )) // Token valid for 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    } // a discutee

    // Extract the username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract the expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a specific claim from the JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (JwtException e) {
            throw new IllegalArgumentException("JWT Token claims could not be resolved: " + e.getMessage());
        }
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid JWT signature: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Expired JWT token: " + e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid JWT token format: " + e.getMessage());
        }
    }

    // Validate the token against user details and expiration
    public boolean validateToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return false;
            }
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }


    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }


    // Get the signing key for JWT token
    private Key getSignKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
            return Keys.hmacShaKeyFor(keyBytes);

    }
}