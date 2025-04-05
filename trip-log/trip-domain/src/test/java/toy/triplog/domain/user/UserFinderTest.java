package toy.triplog.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toy.triplog.domain.auth.PasswordManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserFinderTest {

    @InjectMocks
    private UserFinder userFinder;
    @Mock
    private PasswordManager passwordManager;
    @Mock
    private UserRepository userRepository;

    @DisplayName("로그인 정보를 통해 존재하는 유저를 찾는다.")
    @Test
    void findUserBySign() {
        // given
        User savedUser = createUser();
        given(userRepository.findByEmail("email@email.com")).willReturn(Optional.of(savedUser));

        UserSignInfo userSignInfo = new UserSignInfo("email@email.com", "password");

        // when
        User user = userFinder.findUserBySign(userSignInfo);

        // then
        assertThat(user)
                .extracting("id", "email", "password", "nickname")
                .contains(savedUser.getId(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getNickname());
    }

    @DisplayName("이메일에 해당하는 유저를 찾을 수 없는 경우, 예외가 발생한다.")
    @Test
    void findUserBySignWithWrongEmail() {
        // given
        given(userRepository.findByEmail(any())).willReturn(Optional.empty());

        // when - then
        assertThatThrownBy(() -> userFinder.findUserBySign(new UserSignInfo("email@email.com", "password")))
                .isInstanceOf(NotFoundUserException.class);
    }

    @DisplayName("유저의 잘못된 비밀번호가 입력된 경우, 예외가 발생한다.")
    @Test
    void findUserBySignWithWrongPassword() {
        // given
        User savedUser = createUser();
        given(userRepository.findByEmail(any())).willReturn(Optional.of(savedUser));

        willThrow(new NotFoundUserException("잘못된 아이디 또는 비밀번호입니다."))
                .given(passwordManager)
                .validatePassword(any(), any());

        // when - then
        assertThatThrownBy(() -> userFinder.findUserBySign(new UserSignInfo("email@email.com", "password")))
                .isInstanceOf(NotFoundUserException.class);
    }

    private User createUser() {
        return User.builder()
                .id(1L)
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .build();
    }

}