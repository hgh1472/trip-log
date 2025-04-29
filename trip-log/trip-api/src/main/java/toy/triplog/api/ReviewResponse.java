package toy.triplog.api;

import toy.triplog.domain.nation.Review;

public record ReviewResponse(
        int food,
        int cleanliness,
        int safety,
        int attraction
) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getFood(),
                review.getCleanliness(),
                review.getSafety(),
                review.getAttraction()
        );
    }
}
