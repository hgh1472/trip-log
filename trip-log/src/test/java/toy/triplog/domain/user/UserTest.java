package toy.triplog.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("회원가입 요청으로부터 유저 도메인 객체를 생성한다.")
    @Test
    void create() {
        // given
        UserSignInfo userSignInfo = new UserSignInfo("email@email.com", "password");
        UserDetailInfo userDetailInfo = new UserDetailInfo("nickname");

        // when
        User user = User.of(userSignInfo, userDetailInfo);

        // then
        assertThat(user)
                .extracting("id", "email", "password", "nickname")
                .contains("email@email.com", "password", "nickname");

    }

}