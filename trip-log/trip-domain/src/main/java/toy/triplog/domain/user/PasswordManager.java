package toy.triplog.domain.user;

public interface PasswordManager {

    UserSignInfo encodePassword(UserSignInfo userSignInfo);

    boolean validatePassword(User user, UserSignInfo userSignInfo);

}
