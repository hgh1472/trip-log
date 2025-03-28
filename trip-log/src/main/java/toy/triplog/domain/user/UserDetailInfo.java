package toy.triplog.domain.user;

import lombok.Getter;

@Getter
public class UserDetailInfo {
    private final String nickname;

    public UserDetailInfo(String nickname) {
        this.nickname = nickname;
    }

}
