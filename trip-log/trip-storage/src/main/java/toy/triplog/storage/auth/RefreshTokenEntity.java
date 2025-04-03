package toy.triplog.storage.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refresh-token")
public class RefreshTokenEntity {

    @Id
    private String refreshToken;

    @Indexed
    private Long userId;

    @TimeToLive
    private Long expiration;

    public RefreshTokenEntity(String refreshToken, Long userId, Long expiration) {
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.expiration = expiration;
    }

    public static RefreshTokenEntity of(String refreshToken, Long userId, Long expiration) {
        return new RefreshTokenEntity(refreshToken, userId, expiration);
    }
}
