package toy.triplog.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.triplog.security.PasswordManager;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordManager passwordManager;
    private final UserCreator userCreator;

    @Transactional
    public User createUser(UserSignInfo userSignInfo, UserDetailInfo userDetailInfo) {
        UserSignInfo encodedLoginInfo = passwordManager.encodePassword(userSignInfo);
        User user = User.of(encodedLoginInfo, userDetailInfo);
        return userCreator.create(user);
    }

}
