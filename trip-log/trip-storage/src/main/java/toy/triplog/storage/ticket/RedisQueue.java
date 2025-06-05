package toy.triplog.storage.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import toy.triplog.domain.ticket.Token;
import toy.triplog.domain.ticket.WaitingInfo;
import toy.triplog.domain.ticket.WaitingQueue;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RedisQueue implements WaitingQueue {
    private static final String QUEUE_KEY = "waiting";

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void addUser(Token token, long registerTime) {
        Long counter = redisTemplate.opsForValue().increment(QUEUE_KEY + ":count");
        redisTemplate.opsForZSet().add(QUEUE_KEY, token.getToken(), registerTime * 1000 + counter);
    }

    @Override
    public WaitingInfo getPosition(Token token) {
        Long rank = redisTemplate.opsForZSet().rank(QUEUE_KEY, token.getToken());
        if (rank == null) {
            throw new IllegalArgumentException("해당 사용자가 대기열에 없습니다.");
        }
        Long size = redisTemplate.opsForZSet().size(QUEUE_KEY);
        return new WaitingInfo(size, rank);
    }

    @Override
    public Set<Token> popUsers(int willEnterUserCount) {
        Set<Token> tokens = redisTemplate.opsForZSet().range(QUEUE_KEY, 0, willEnterUserCount - 1)
                .stream()
                .map(token -> Token.builder()
                        .token(UUID.fromString(token))
                        .build())
                .collect(Collectors.toSet());
        redisTemplate.opsForZSet().removeRange(QUEUE_KEY, 0, willEnterUserCount - 1);
        return tokens;
    }

    @Override
    public void clear() {
        redisTemplate.opsForZSet().removeRange(QUEUE_KEY, 0, -1);
    }
}
