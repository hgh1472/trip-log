package toy.triplog.domain.nation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NationWithScore {

    private Nation nation;
    private AverageScore averageScore;

    public static NationWithScore of(Nation nation, AverageScore averageScore) {
        return NationWithScore.builder()
                .nation(nation)
                .averageScore(averageScore)
                .build();
    }

}
