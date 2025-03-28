package toy.triplog.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.triplog.storage.user.JpaUserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserCreatorTest {

    @Autowired
    private UserCreator userCreator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JpaUserRepository jpaUserRepository;

    @AfterEach
    void tearDown() {
        jpaUserRepository.deleteAllInBatch();
    }

    @DisplayName("유저를 생성한다.")
    @Test
    void create() {
        // given
        User user = User.builder()
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .build();

        // when
        User savedUser = userCreator.create(user);

        // then
        Optional<User> findUser = userRepository.findById(savedUser.getId());
        assertThat(findUser).isPresent();
    }

}