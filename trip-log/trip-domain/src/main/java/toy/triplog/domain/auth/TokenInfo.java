package toy.triplog.domain.auth;

import lombok.Getter;

@Getter
public class TokenInfo {

    private String accessToken;

    private String refreshToken;

    private TokenInfo(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenInfo of(String accessToken, String refreshToken) {
        return new TokenInfo(accessToken, refreshToken);
    }

}
