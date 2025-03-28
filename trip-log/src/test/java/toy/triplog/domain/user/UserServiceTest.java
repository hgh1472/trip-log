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
class UserServiceTest {

    @Autowired
    private UserService userService;
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
    void createUser() {
        // given
        UserSignInfo userSignInfo = new UserSignInfo("email@email.com", "password");
        UserDetailInfo userDetailInfo = new UserDetailInfo("nickname");

        // when
        User user = userService.createUser(userSignInfo, userDetailInfo);

        // then
        Optional<User> findUser = userRepository.findById(user.getId());
        assertThat(findUser).isPresent();
    }

}