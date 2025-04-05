package toy.triplog.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toy.triplog.domain.auth.PasswordManager;
import toy.triplog.domain.auth.TokenAppender;
import toy.triplog.domain.auth.TokenInfo;
import toy.triplog.domain.auth.TokenProvider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordManager passwordManager;
    @Mock
    private TokenAppender tokenAppender;
    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private UserCreator userCreator;
    @Mock
    private UserFinder userFinder;

    @DisplayName("유저를 생성한다.")
    @Test
    void createUser() {
        // given
        UserSignInfo userSignInfo = new UserSignInfo("email@email.com", "password");
        given(passwordManager.encodePassword(userSignInfo)).willReturn(new UserSignInfo("email@email.com", "encoded"));
        UserDetailInfo userDetailInfo = new UserDetailInfo("nickname");

        // when
        userService.createUser(userSignInfo, userDetailInfo);

        // then
        verify(userCreator, times(1)).create(any());
        verify(passwordManager, times(1)).encodePassword(userSignInfo);
    }

    @DisplayName("유저가 로그인한다.")
    @Test
    void login() {
        // given
        UserSignInfo userSignInfo = new UserSignInfo("email@email.com", "password");
        given(userFinder.findUserBySign(userSignInfo)).willReturn(User.of(userSignInfo, new UserDetailInfo("nickname")));
        given(tokenProvider.generateToken(any())).willReturn(TokenInfo.of("access", "refresh"));

        // when
        userService.login(userSignInfo);

        // then
        verify(userFinder, times(1)).findUserBySign(userSignInfo);
        verify(tokenProvider, times(1)).generateToken(any());
        verify(tokenAppender, times(1)).appendRefreshToken(any(), any());
    }

}