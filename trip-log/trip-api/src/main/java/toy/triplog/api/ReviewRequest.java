package toy.triplog.api;

import toy.triplog.domain.auth.ApiUser;
import toy.triplog.domain.nation.WrittenReview;

public record ReviewRequest(
        int food,
        int cleanliness,
        int safety,
        int attraction
) {
    public WrittenReview toWrittenReview(Long nationId, ApiUser apiUser) {
        return new WrittenReview(
                nationId,
                apiUser.id(),
                food,
                cleanliness,
                safety,
                attraction
        );
    }
}
