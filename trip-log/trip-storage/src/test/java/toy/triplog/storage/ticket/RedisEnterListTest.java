package toy.triplog.storage.ticket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import toy.triplog.StorageContextTest;
import toy.triplog.domain.ticket.Token;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class RedisEnterListTest extends StorageContextTest {
    private static final String ENTER_LIST_KEY_PREFIX = "enter_list:";
    private static final int EXPIRE_MINUTE = 5;

    @Autowired
    private RedisEnterList redisEnterList;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @AfterEach
    void tearDown() {
    }

    @DisplayName("유저가 입장했는지 체크한다.")
    @Test
    void doesUserEntered() {
        // given
        Token token = Token.builder().token(UUID.randomUUID()).build();
        redisTemplate.opsForValue().set(ENTER_LIST_KEY_PREFIX + token.getToken(), token.getToken(), EXPIRE_MINUTE, TimeUnit.MINUTES);

        // when
        boolean doesUserEntered = redisEnterList.doesUserEntered(token);

        // then
        assertThat(doesUserEntered).isTrue();
    }

    @DisplayName("사용자를 입장 리스트에 추가한다.")
    @Test
    void addUsers() {
        // given
        Set<Token> tokens = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            tokens.add(Token.builder().token(UUID.randomUUID()).build());
        }

        // when
        redisEnterList.addUsers(tokens, EXPIRE_MINUTE);

        // then
        tokens.forEach(token -> {
            String value = redisTemplate.opsForValue().get(ENTER_LIST_KEY_PREFIX + token.getToken());
            assertThat(value).isNotNull();
            assertThat(value).isEqualTo(token.getToken());
        });
    }

    @DisplayName("입장 리스트 내 토큰의 TTL을 갱신한다.")
    @Test
    void updateAvailableTime() {
        // given
        Token token = Token.builder().token(UUID.randomUUID()).build();

        int beforeUpdate = 10;
        redisTemplate.opsForValue().set(ENTER_LIST_KEY_PREFIX + token.getToken(), token.getToken(), beforeUpdate, TimeUnit.MINUTES);
        Long beforeUpdateExpire = redisTemplate.getExpire(ENTER_LIST_KEY_PREFIX + token.getToken());

        int afterUpdate = 5;

        // when
        redisEnterList.updateAvailableTime(token, afterUpdate);

        // then
        Long afterUpdateExpire = redisTemplate.getExpire(ENTER_LIST_KEY_PREFIX + token.getToken());
        assertThat(afterUpdateExpire).isLessThan(beforeUpdateExpire);
    }

}