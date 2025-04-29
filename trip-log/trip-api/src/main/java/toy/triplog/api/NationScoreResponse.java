package toy.triplog.api;

public record NationScoreResponse(
        NationResponse nation,
        ScoreResponse score
) {
}
