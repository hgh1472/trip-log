package toy.triplog.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.triplog.domain.auth.TokenAppender;
import toy.triplog.domain.auth.TokenProvider;
import toy.triplog.domain.auth.TokenInfo;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordManager passwordManager;
    private final TokenProvider tokenProvider;
    private final TokenAppender tokenAppender;

    private final UserCreator userCreator;
    private final UserFinder userFinder;

    @Transactional
    public User createUser(UserSignInfo userSignInfo, UserDetailInfo userDetailInfo) {
        UserSignInfo encodedLoginInfo = passwordManager.encodePassword(userSignInfo);
        User user = User.of(encodedLoginInfo, userDetailInfo);
        return userCreator.create(user);
    }

    @Transactional
    public TokenInfo login(UserSignInfo userSignInfo) {
        User user = userFinder.findUserBySign(userSignInfo);
        TokenInfo tokenInfo = tokenProvider.generateToken(user);
        tokenAppender.appendRefreshToken(tokenInfo, user);
        return tokenInfo;
    }

}
