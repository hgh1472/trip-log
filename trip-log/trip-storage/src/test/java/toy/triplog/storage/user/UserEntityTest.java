package toy.triplog.storage.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.triplog.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest extends StorageContextTest {

    @DisplayName("유저 도메인 객체로부터 유저 엔티티로 변환한다.")
    @Test
    void from() {
        // given
        User user = User.builder()
                .id(1L)
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .build();

        // when
        UserEntity userEntity = UserEntity.from(user);

        // then
        assertThat(userEntity)
                .extracting("id", "email", "password", "nickname")
                .contains(1L, "email@email.com", "password", "nickname");
    }

    @DisplayName("유저 엔티티로부터 유저 도메인으로 변환한다.")
    @Test
    void toUser() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .build();

        // when
        User user = userEntity.toUser();

        // then
        assertThat(user)
                .extracting("id", "email", "password", "nickname")
                .contains(1L, "email@email.com", "password", "nickname");
    }

}