package toy.triplog.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFinder {

    private final UserRepository userRepository;
    private final PasswordManager passwordManager;

    public User findUserBySign(UserSignInfo userSignInfo) {
        User user = userRepository.findByEmail(userSignInfo.getEmail())
                .orElseThrow(() -> new NotFoundUserException("잘못된 아이디 또는 비밀번호입니다."));
        passwordManager.validatePassword(user, userSignInfo);
        return user;
    }

}
