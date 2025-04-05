package toy.triplog.domain.auth;

public record ApiUser(
        long id
) {
    public static ApiUser of(long id) {
        return new ApiUser(id);
    }
}
