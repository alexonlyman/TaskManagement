package alexgr.taskmanagement.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Service class for handling JWT (JSON Web Token) operations.
 * Provides methods for generating, validating, and extracting information from tokens.
 */
@Service
public class JwtTokenService {

    private final Logger logger = Logger.getAnonymousLogger();

    /**
     * The secret key used for signing and validating JWT tokens.
     * This key is injected from the application properties.
     */
    @Value("${security.jwt.key}")
    private String KEY;

    /**
     * Extracts the username (subject) from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    public String extractUserName(String token) {
        String username = extractClaim(token, Claims::getSubject);
        logger.info("Extracted username: " + username);
        return username;
    }

    /**
     * Extracts a specific claim from the provided JWT token using the given function.
     *
     * @param token          the JWT token.
     * @param claimsTFunction a function to extract the desired claim.
     * @param <T>            the type of the claim.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    /**
     * Generates a new JWT token for the provided user details.
     *
     * @param userDetails the user details for whom the token is generated.
     * @return the generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .filter(authority -> authority != null && authority.getAuthority() != null)
                .map(GrantedAuthority::getAuthority)
                .toList());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
                .signWith(getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the provided JWT token for the given user details.
     *
     * @param token       the JWT token.
     * @param userDetails the user details to validate against.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    public boolean IsTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !IsTokenExpired(token));
    }

    /**
     * Checks if the provided JWT token is expired.
     *
     * @param token the JWT token.
     * @return {@code true} if the token is expired, {@code false} otherwise.
     */
    private boolean IsTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
    }

    /**
     * Extracts the expiration date from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the expiration date of the token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the claims contained in the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used for JWT token operations.
     *
     * @return the signing key.
     */
    private Key getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
