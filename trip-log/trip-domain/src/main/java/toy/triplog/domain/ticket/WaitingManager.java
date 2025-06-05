package toy.triplog.domain.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class WaitingManager {

    private final WaitingQueue waitingQueue;
    private final EnterList enterList;

    public Token joinWaitingQueue() {
        Token token = Token.builder()
                .token(UUID.randomUUID())
                .build();
        waitingQueue.addUser(token, System.currentTimeMillis());
        return token;
    }

    public WaitingInfo findWaitingOrder(Token token) {
        return waitingQueue.getPosition(token);
    }

    public void checkUserEntered(Token token) {
        if (enterList.doesUserEntered(token)) {
            return;
        }
        throw new RuntimeException("아직 입장하지 않은 사용자입니다.");
    }

    public void updateAvailableTime(Token token, int expireMinute) {
        enterList.updateAvailableTime(token, expireMinute);
    }

    public void enterUsers(int enterUserUnit, int expireMinute) {
        Set<Token> tokens = waitingQueue.popUsers(enterUserUnit);
        enterList.addUsers(tokens, expireMinute);
    }

}
