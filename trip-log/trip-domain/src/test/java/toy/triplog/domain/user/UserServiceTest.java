package toy.triplog.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toy.triplog.domain.auth.PasswordManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserCreator userCreator;
    @Mock
    private PasswordManager passwordManager;

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

}