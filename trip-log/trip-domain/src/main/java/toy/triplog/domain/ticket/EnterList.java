package toy.triplog.domain.ticket;

import java.util.Set;

public interface EnterList {

    boolean doesUserEntered(Token token);

    void addUsers(Set<Token> tokens, int expireMinute);

    void updateAvailableTime(Token token, int expireMinute);
}
