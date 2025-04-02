package toy.triplog.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserCreatorTest {

    @InjectMocks
    private UserCreator userCreator;
    @Mock
    private UserRepository userRepository;

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
        userCreator.create(user);

        // then
        verify(userRepository, times(1)).save(user);
    }

}