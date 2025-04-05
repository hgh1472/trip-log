package toy.triplog.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.triplog.domain.user.User;
import toy.triplog.domain.user.UserSignInfo;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityPasswordManagerTest extends SecurityContextTest {

    @Autowired
    private SecurityPasswordManager passwordManager;

    @DisplayName("유저의 패스워드를 암호화한 로그인 정보를 반환한다.")
    @Test
    void encodePassword() {
        // given
        UserSignInfo userSignInfo = new UserSignInfo("email@email.com", "password");

        // when
        UserSignInfo encodedUserSignInfo = passwordManager.encodePassword(userSignInfo);

        // then
        assertThat(encodedUserSignInfo.getEmail()).isEqualTo("email@email.com");
        assertThat(encodedUserSignInfo.getPassword()).isNotEqualTo("password");
    }

    @DisplayName("암호회된 유저의 비밀번호와 실제 비밀번호의 일치 여부를 확인한다.")
    @Test
    void validatePassword() {
        // given
        UserSignInfo encodedUserSignInfo = passwordManager.encodePassword(new UserSignInfo("email@email.com", "password"));

        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password(encodedUserSignInfo.getPassword())
                .nickname("tester")
                .build();

        // when
        boolean result = passwordManager.validatePassword(user, new UserSignInfo("email@email.com", "password"));

        // then
        assertThat(result).isTrue();
    }

}