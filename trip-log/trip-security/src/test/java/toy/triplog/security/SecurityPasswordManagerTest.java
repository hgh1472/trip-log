package toy.triplog.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.triplog.domain.user.NotFoundUserException;
import toy.triplog.domain.user.User;
import toy.triplog.domain.user.UserSignInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SecurityPasswordManagerTest extends SecurityContextTest {

    @Autowired
    private SecurityPasswordManager passwordManager;

    private UserSignInfo userSignInfo;

    @BeforeEach
    void setUp() {
        userSignInfo = new UserSignInfo("email@email.com", "password");
    }

    @DisplayName("유저의 패스워드를 암호화한 로그인 정보를 반환한다.")
    @Test
    void encodePassword() {
        // when
        UserSignInfo encodedUserSignInfo = passwordManager.encodePassword(userSignInfo);

        // then
        assertThat(encodedUserSignInfo.getEmail()).isEqualTo("email@email.com");
        assertThat(encodedUserSignInfo.getPassword()).isNotEqualTo("password");
    }

    @DisplayName("암호화된 유저의 비밀번호와 실제 비밀번호의 일치하면 예외를 발생시키지 않는다.")
    @Test
    void validateCorrectPassword() {
        // given
        UserSignInfo encodedUserSignInfo = passwordManager.encodePassword(userSignInfo);

        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password(encodedUserSignInfo.getPassword())
                .nickname("tester")
                .build();

        // when - then
        assertDoesNotThrow(() -> passwordManager.validatePassword(user, userSignInfo));
    }

    @DisplayName("암호화된 유저의 비밀번호와 실제 비밀번호의 일치하지 않으면 예외가 발생한다.")
    @Test
    void validateWrongPassword() {
        // given
        UserSignInfo encodedUserSignInfo = passwordManager.encodePassword(userSignInfo);

        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password(encodedUserSignInfo.getPassword())
                .nickname("tester")
                .build();

        // when - then
        assertThatThrownBy(() -> passwordManager.validatePassword(user, new UserSignInfo("email@email.com", "wrong")))
                .isInstanceOf(NotFoundUserException.class);
    }

}