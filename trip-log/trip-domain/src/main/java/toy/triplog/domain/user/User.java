package toy.triplog.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;

    @Builder
    private User(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static User of(UserSignInfo userSignInfo, UserDetailInfo userDetailInfo) {
        return User.builder()
                .email(userSignInfo.getEmail())
                .password(userSignInfo.getPassword())
                .nickname(userDetailInfo.getNickname())
                .build();
    }

}
