package toy.triplog.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDetailInfoTest {

    @DisplayName("유저 로그인 정보의 패스워드를 변경한다.")
    @Test
    void changePassword() {
        // given
        UserSignInfo userSignInfo = new UserSignInfo("email@email.com", "password");

        // when
        UserSignInfo changed = userSignInfo.changePassword("changed");

        // then
        assertThat(changed.getPassword()).isEqualTo("changed");
    }

}