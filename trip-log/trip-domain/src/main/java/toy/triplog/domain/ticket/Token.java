package toy.triplog.domain.ticket;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Token {
    UUID token;

    public String getToken() {
        return token.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(token);
    }
}
