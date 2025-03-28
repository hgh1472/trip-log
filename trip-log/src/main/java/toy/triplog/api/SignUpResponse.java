package toy.triplog.api;

import toy.triplog.domain.user.User;

public record SignUpResponse(
        long userId,
        String email,
        String nickname
) {
    public static SignUpResponse from(User user) {
        return new SignUpResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
    }
}
