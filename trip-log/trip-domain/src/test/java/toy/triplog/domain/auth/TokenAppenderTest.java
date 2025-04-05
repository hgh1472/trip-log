package toy.triplog.domain.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import toy.triplog.domain.user.User;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TokenAppenderTest {

    private TokenAppender tokenAppender;

    private static final long REFRESH_EXPIRATION = 60;

    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void setUp() {
        refreshTokenRepository = Mockito.mock(RefreshTokenRepository.class);
        this.tokenAppender = new TokenAppender(REFRESH_EXPIRATION, refreshTokenRepository);
    }

    @DisplayName("리프레시 토큰을 저장한다.")
    @Test
    void appendRefreshToken() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password("password")
                .nickname("nickname")
                .build();
        TokenInfo tokenInfo = TokenInfo.of("access", "refresh");

        // when
        tokenAppender.appendRefreshToken(tokenInfo, user);

        // then
        verify(refreshTokenRepository, times(1)).save(tokenInfo.getRefreshToken(), user.getId(), REFRESH_EXPIRATION);
    }

}
