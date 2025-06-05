package toy.triplog.storage.ticket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import toy.triplog.StorageContextTest;
import toy.triplog.domain.ticket.Token;
import toy.triplog.domain.ticket.WaitingInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RedisQueueTest extends StorageContextTest {

    private static final String QUEUE_KEY = "waiting";

    @Autowired
    private RedisQueue redisQueue;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @AfterEach
    void tearDown() {
        redisTemplate.delete(QUEUE_KEY);
    }

    @DisplayName("Sorted Set에 사용자를 입장 시간을 이용해 추가한다.")
    @Test
    void addUser() {
        // given
        Token token = Token.builder()
                .token(UUID.randomUUID())
                .build();
        long registerTime = System.currentTimeMillis();

        // when
        redisQueue.addUser(token, registerTime);

        // then
        Long rank = redisTemplate.opsForZSet().rank(QUEUE_KEY, token.getToken());
        assertThat(rank).isNotNull();
        assertThat(rank).isEqualTo(0L);
    }

    @DisplayName("대기열 내 토큰의 순서를 조회한다.")
    @Test
    void getPosition() {
        // given
        Token secondToken = Token.builder()
                .token(UUID.randomUUID())
                .build();
        redisQueue.addUser(Token.builder().token(UUID.randomUUID()).build(), System.currentTimeMillis());
        redisQueue.addUser(secondToken, System.currentTimeMillis());
        redisQueue.addUser(Token.builder().token(UUID.randomUUID()).build(), System.currentTimeMillis());

        // when
        WaitingInfo position = redisQueue.getPosition(secondToken);

        // then
        assertThat(position.total()).isEqualTo(3);
        assertThat(position.rank()).isEqualTo(1);
    }

    @DisplayName("대기열에서 앞 순위의 사용자들을 꺼낸다.")
    @Test
    void popUsers() {
        // given
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Token token = Token.builder()
                    .token(UUID.randomUUID())
                    .build();
            tokens.add(token);
            redisQueue.addUser(token, System.currentTimeMillis());
        }

        // when
        Set<Token> poppedTokens = redisQueue.popUsers(5);

        // then
        assertThat(poppedTokens).hasSize(5);
        assertThat(poppedTokens).containsAll(tokens.subList(0, 5));
    }
}
