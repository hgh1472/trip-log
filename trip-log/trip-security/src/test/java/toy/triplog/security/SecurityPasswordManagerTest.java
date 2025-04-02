package toy.triplog.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

}