package toy.triplog.domain.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.triplog.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;

class TokenResolverTest {

    private static final String SECRET = "secret".repeat(30);
    private static final long ACCESS_EXPIRATION = 1000 * 60 * 10;
    private static final long REFRESH_EXPIRATION = 1000 * 60 * 10;

    private TokenResolver tokenResolver;
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        tokenResolver = new TokenResolver(SECRET);
        tokenProvider = new TokenProvider(ACCESS_EXPIRATION, REFRESH_EXPIRATION, SECRET);
    }

    @DisplayName("엑세스 토큰에서 유저 정보를 추출한다.")
    @Test
    void resolveToken() {
        // given
        User user = User.builder()
                .id(1L)
                .email("email")
                .password("password")
                .nickname("nickname")
                .build();
        TokenInfo tokenInfo = tokenProvider.generateToken(user);

        // when
        ApiUser userPrincipal = tokenResolver.resolveToken(tokenInfo.getAccessToken());

        // then
        assertThat(userPrincipal.id()).isEqualTo(1);
    }

}