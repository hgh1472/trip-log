package toy.triplog.api;

import toy.triplog.domain.nation.AverageScore;

public record ScoreResponse(
        double total,
        double food,
        double cleanliness,
        double safety,
        double attraction
) {
    public static ScoreResponse from(AverageScore averageScore) {
        return new ScoreResponse(
                averageScore.getTotal(),
                averageScore.getFood(),
                averageScore.getCleanliness(),
                averageScore.getSafety(),
                averageScore.getAttraction()
        );
    }
}
