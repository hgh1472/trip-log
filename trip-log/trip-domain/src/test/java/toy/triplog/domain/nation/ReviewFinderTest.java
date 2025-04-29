package toy.triplog.domain.nation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.triplog.domain.DomainTestContext;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewFinderTest extends DomainTestContext {

    @Autowired
    private ReviewFinder reviewFinder;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private NationGenerator nationGenerator;

    @DisplayName("나라의 평균 점수를 조회한다.")
    @Test
    void findAverageScore() {
        // given
        Nation nation = nationGenerator.generateNation("한국", Continent.ASIA);
        for (int i = 0; i < 5; i++) {
            reviewRepository.save(createReview(nation.getId(), 1 + i, 2 + i, 3 + i, 4 + i));
        }

        // when
        AverageScore averageScore = reviewFinder.findAverageScore(nation.getId());

        // then
        assertThat(averageScore)
                .extracting("total", "attraction", "food", "safety", "cleanliness")
                .containsExactly(4.5, 3.0, 4.0, 5.0, 6.0);
    }

    @DisplayName("페이지에 있는 리뷰들을 조회한다.")
    @Test
    void findReviewsAtPage() {
        // given
        Nation nation = nationGenerator.generateNation("한국", Continent.ASIA);
        for (int i = 0; i < 15; i++) {
            reviewRepository.save(createReview(nation.getId(), 5, 5, 5, 5));
        }

        // when
        PageData<Review> reviewsAtPage = reviewFinder.findReviewsAtPage(nation.getId(), 1, 10);

        // then
        assertThat(reviewsAtPage.getData()).hasSize(10);
        assertThat(reviewsAtPage.getTotalPages()).isEqualTo(2);
        assertThat(reviewsAtPage.getPage()).isEqualTo(1);
        assertThat(reviewsAtPage.getTotalElements()).isEqualTo(15);
    }

    private static Review createReview(Long nationId, int attraction, int food, int safety, int cleanliness) {
        return Review.builder()
                .nationId(nationId)
                .userId(1L)
                .attraction(attraction)
                .food(food)
                .safety(safety)
                .cleanliness(cleanliness)
                .build();
    }

}