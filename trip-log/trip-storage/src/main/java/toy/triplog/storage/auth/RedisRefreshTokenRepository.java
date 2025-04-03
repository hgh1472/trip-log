package toy.triplog.storage.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface RedisRefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {
}
