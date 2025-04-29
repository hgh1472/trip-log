package toy.triplog.domain.nation;

public record WrittenReview(
        long nationId,
        long userId,
        int food,
        int cleanliness,
        int safety,
        int attraction
) {
}
