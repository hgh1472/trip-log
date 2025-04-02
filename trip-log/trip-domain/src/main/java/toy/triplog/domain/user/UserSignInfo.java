package toy.triplog.domain.user;

import lombok.Getter;

@Getter
public class UserSignInfo {

    private final String email;

    private final String password;

    public UserSignInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserSignInfo changePassword(String password) {
        return new UserSignInfo(email, password);
    }

}
