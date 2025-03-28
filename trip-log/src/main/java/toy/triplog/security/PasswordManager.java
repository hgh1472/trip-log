package toy.triplog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import toy.triplog.domain.user.UserSignInfo;

@Component
@RequiredArgsConstructor
public class PasswordManager {

    private final PasswordEncoder passwordEncoder;

    public UserSignInfo encodePassword(UserSignInfo userSignInfo) {
        String encodedPassword = passwordEncoder.encode(userSignInfo.getPassword());
        return userSignInfo.changePassword(encodedPassword);
    }

}
