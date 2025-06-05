package toy.triplog.domain.ticket;

import java.util.Set;

public interface WaitingQueue {

    void addUser(Token token, long registerTime);

    WaitingInfo getPosition(Token token);

    Set<Token> popUsers(int willEnterUserCount);

    void clear();
}
