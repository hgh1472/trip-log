package toy.triplog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import toy.triplog.domain.auth.PasswordManager;
import toy.triplog.domain.user.NotFoundUserException;
import toy.triplog.domain.user.User;
import toy.triplog.domain.user.UserSignInfo;

@Component
@RequiredArgsConstructor
public class SecurityPasswordManager implements PasswordManager {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserSignInfo encodePassword(UserSignInfo userSignInfo) {
        String encodedPassword = passwordEncoder.encode(userSignInfo.getPassword());
        return userSignInfo.changePassword(encodedPassword);
    }

    @Override
    public void validatePassword(User user, UserSignInfo userSignInfo) {
        if (passwordEncoder.matches(userSignInfo.getPassword(), user.getPassword())) {
            return;
        }
        throw new NotFoundUserException("잘못된 아이디 또는 비밀번호입니다.");
    }

}
