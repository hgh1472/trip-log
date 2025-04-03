package toy.triplog.domain.auth;

public interface RefreshTokenRepository {

    void save(String refreshToken, long userId, long expiration);

}
