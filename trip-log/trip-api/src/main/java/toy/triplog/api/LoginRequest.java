package toy.triplog.api;

import toy.triplog.domain.user.UserSignInfo;

public record LoginRequest(
        String email,
        String password
) {
    public UserSignInfo toUserSignInfo() {
        return new UserSignInfo(email, password);
    }
}
