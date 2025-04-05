package toy.triplog.domain.auth;

import toy.triplog.domain.user.User;
import toy.triplog.domain.user.UserSignInfo;

public interface PasswordManager {

    UserSignInfo encodePassword(UserSignInfo userSignInfo);

    void validatePassword(User user, UserSignInfo userSignInfo);

}
