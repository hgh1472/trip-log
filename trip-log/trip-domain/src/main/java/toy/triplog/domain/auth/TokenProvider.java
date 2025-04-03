package toy.triplog.domain.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import toy.triplog.domain.user.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {

    private final long accessExpiration;
    private final long refreshExpiration;

    private final SecretKey secretKey;

    public TokenProvider(@Value("${jwt.expiration.access}") long accessExpiration,
                         @Value("${jwt.expiration.refresh}") long refreshExpiration,
                         @Value("${spring.jwt.secret}") String secret) {
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public TokenInfo generateToken(User user) {
        String accessToken = createTokenWith(user, accessExpiration);
        String refreshToken = createTokenWith(user, refreshExpiration);
        return TokenInfo.of(accessToken, refreshToken);
    }

    private String createTokenWith(User user, long expiration) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .claim("id", user.getId())
                .expiration(validity)
                .issuedAt(now)
                .signWith(secretKey)
                .compact();
    }

}
