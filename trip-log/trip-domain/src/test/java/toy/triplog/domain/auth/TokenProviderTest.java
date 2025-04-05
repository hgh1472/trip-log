package toy.triplog.domain.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.triplog.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;

class TokenProviderTest {

    private static final String secret = "secret".repeat(30);

    private final TokenProvider tokenProvider = new TokenProvider(60, 60, secret);

    @DisplayName("토큰을 생성한다.")
    @Test
    void generateToken() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password("test")
                .nickname("tester")
                .build();

        // when
        TokenInfo tokenInfo = tokenProvider.generateToken(user);

        // then
        assertThat(tokenInfo.getAccessToken()).isNotNull();
        assertThat(tokenInfo.getRefreshToken()).isNotNull();
    }

}