package toy.triplog.storage.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import toy.triplog.domain.ticket.EnterList;
import toy.triplog.domain.ticket.Token;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisEnterList implements EnterList {

    private static final String ENTER_LIST_KEY_PREFIX = "enter_list:";

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean doesUserEntered(Token token) {
        Boolean isEntered = redisTemplate.hasKey(ENTER_LIST_KEY_PREFIX + token.getToken());
        return Boolean.TRUE.equals(isEntered);
    }

    @Override
    public void addUsers(Set<Token> tokens, int expireMinute) {
        for (Token token : tokens) {
            redisTemplate.opsForValue().set(ENTER_LIST_KEY_PREFIX + token.getToken(), token.getToken(), expireMinute, TimeUnit.MINUTES);
        }
    }

    @Override
    public void updateAvailableTime(Token token, int expireMinute) {
        redisTemplate.expire(ENTER_LIST_KEY_PREFIX + token.getToken(), expireMinute, TimeUnit.MINUTES);
    }

}
