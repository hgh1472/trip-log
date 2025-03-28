package toy.triplog.storage.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.triplog.domain.user.User;
import toy.triplog.domain.user.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JpaUserRepository jpaUserRepository;

    @AfterEach
    void tearDown() {
        jpaUserRepository.deleteAllInBatch();
    }

    @DisplayName("유저를 새로 저장한다.")
    @Test
    void save() {
        // given
        User user = User.builder()
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .build();

        // when
        User savedUser = userRepository.save(user);

        // then
        Optional<User> findUser = userRepository.findById(savedUser.getId());
        assertThat(findUser).isPresent();
    }

}