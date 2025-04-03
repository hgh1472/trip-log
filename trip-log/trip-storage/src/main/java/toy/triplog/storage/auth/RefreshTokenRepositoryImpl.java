package toy.triplog.storage.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.triplog.domain.auth.RefreshTokenRepository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    @Override
    public void save(String refreshToken, long userId, long expiration) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.of(refreshToken, userId, expiration);
        redisRefreshTokenRepository.save(refreshTokenEntity);
    }
}
