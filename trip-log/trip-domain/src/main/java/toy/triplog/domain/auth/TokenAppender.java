package toy.triplog.domain.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import toy.triplog.domain.user.User;

@Component
public class TokenAppender {

    private final long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public TokenAppender(@Value("${jwt.expiration.refresh}") long refreshExpiration,
                         RefreshTokenRepository refreshTokenRepository) {
        this.refreshExpiration = refreshExpiration;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    public void appendRefreshToken(TokenInfo tokenInfo, User user) {
        refreshTokenRepository.save(tokenInfo.getRefreshToken(), user.getId(), refreshExpiration);
    }


}
