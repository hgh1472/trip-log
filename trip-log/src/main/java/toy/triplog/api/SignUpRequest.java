package toy.triplog.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import toy.triplog.domain.user.UserDetailInfo;
import toy.triplog.domain.user.UserSignInfo;

public record SignUpRequest(
        @Email(message = "이메일 형식이어야 합니다.")
        String email,
        @NotBlank(message = "패스워드를 입력해주세요.")
        String password,
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname
) {
    public UserSignInfo toUserSignInfo() {
        return new UserSignInfo(this.email, this.password);
    }

    public UserDetailInfo toUserDetailInfo() {
        return new UserDetailInfo(this.nickname);
    }
}
