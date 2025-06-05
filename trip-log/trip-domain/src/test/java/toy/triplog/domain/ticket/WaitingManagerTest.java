package toy.triplog.domain.ticket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.triplog.DomainTestContext;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class WaitingManagerTest extends DomainTestContext {

    @Autowired
    private WaitingManager waitingManager;
    @Autowired
    private WaitingQueue waitingQueue;
    @Autowired
    private EnterList enterList;

    @AfterEach
    void tearDown() {
        waitingQueue.clear();
    }

    @DisplayName("대기열에 입장시킨다.")
    @Test
    void joinWaitingQueue() {
        // when
        Token token = waitingManager.joinWaitingQueue();

        // then
        WaitingInfo position = waitingQueue.getPosition(token);
        assertThat(position.rank()).isEqualTo(0);
        assertThat(position.total()).isEqualTo(1);
    }

    @DisplayName("대기열에서 몇번째 순서인지 확인한다.")
    @Test
    void findWaitingOrder() {
        // given
        Token second = Token.builder().token(UUID.randomUUID()).build();
        waitingQueue.addUser(Token.builder().token(UUID.randomUUID()).build(), System.currentTimeMillis());
        waitingQueue.addUser(second, System.currentTimeMillis());

        // when
        WaitingInfo waitingOrder = waitingManager.findWaitingOrder(second);

        // then
        assertThat(waitingOrder.rank()).isEqualTo(1);
        assertThat(waitingOrder.total()).isEqualTo(2);
    }

    @DisplayName("유저가 입장 리스트에 있는지 확인한다.")
    @Test
    void checkUserEntered() {
        // given
        Token token = Token.builder().token(UUID.randomUUID()).build();
        enterList.addUsers(Set.of(token), 5);

        // when then
        assertDoesNotThrow(() -> waitingManager.checkUserEntered(token));
    }

    @DisplayName("입장 리스트에 유저가 없으면 예외를 발생시킨다.")
    @Test
    void checkNotEnteredUser() {
        // given
        Token token = Token.builder().token(UUID.randomUUID()).build();

        // when then
        assertThatThrownBy(() -> waitingManager.checkUserEntered(token))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("아직 입장하지 않은 사용자입니다.");
    }

}
