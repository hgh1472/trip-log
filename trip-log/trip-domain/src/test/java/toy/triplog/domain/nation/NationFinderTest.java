package toy.triplog.domain.nation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.triplog.domain.DomainTestContext;

import static org.assertj.core.api.Assertions.assertThat;


class NationFinderTest extends DomainTestContext {

    @Autowired
    private NationFinder nationFinder;
    @Autowired
    private NationGenerator nationGenerator;
    @Autowired
    private ReviewRepository reviewRepository;

    @DisplayName("나라를 리포지토리로부터 조회한다.")
    @Test
    void findNation() {
        // given
        Nation nation = nationGenerator.generateNation("한국", Continent.ASIA);

        // when
        Nation find = nationFinder.findNation(nation.getId());

        // then
        assertThat(find)
                .extracting("id", "name", "continent")
                .containsExactly(nation.getId(), nation.getName(), nation.getContinent());
    }

    @DisplayName("나라를 평균 점수와 함께 조회한다.")
    @Test
    void findNationWithScore() {
        // given
        Nation nation = nationGenerator.generateNation("한국", Continent.ASIA);
        Review review = Review.builder()
                .nationId(nation.getId())
                .userId(1L)
                .cleanliness(10)
                .safety(10)
                .food(10)
                .attraction(10)
                .build();
        reviewRepository.save(review);

        // when
        NationWithScore find = nationFinder.findNationWithScore(nation.getId());

        // then
        AverageScore averageScore = find.getAverageScore();
        assertThat(averageScore)
                .extracting("total", "food", "cleanliness", "safety", "attraction")
                .containsExactly(10.0, 10.0, 10.0, 10.0, 10.0);
    }
}