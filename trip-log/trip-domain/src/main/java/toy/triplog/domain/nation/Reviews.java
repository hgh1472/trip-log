package toy.triplog.domain.nation;

import lombok.Getter;

import java.util.List;
import java.util.function.ToIntFunction;

@Getter
public class Reviews {

    private final List<Review> reviews;

    private Reviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public static Reviews from(List<Review> reviews) {
        return new Reviews(reviews);
    }

    public AverageScore calculateAverageScore() {
        double food = averageOf(Review::getFood);
        double cleanliness = averageOf(Review::getCleanliness);
        double safety = averageOf(Review::getSafety);
        double attraction = averageOf(Review::getAttraction);

        return AverageScore.of(food, cleanliness, safety, attraction);
    }

    private double averageOf(ToIntFunction<Review> targetScore) {
        return reviews.stream()
                .mapToInt(targetScore)
                .average()
                .orElse(0);
    }

}
