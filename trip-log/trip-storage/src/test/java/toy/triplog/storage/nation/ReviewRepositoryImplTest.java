package toy.triplog.storage.nation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.triplog.domain.nation.*;
import toy.triplog.StorageContextTest;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewRepositoryImplTest extends StorageContextTest {

    @Autowired
    private ReviewRepositoryImpl reviewRepository;

    @Autowired
    private NationRepositoryImpl nationRepository;

    @DisplayName("페이지의 리뷰를 가져온다.")
    @Test
    void findReviewsAtPage() {
        // given
        Nation nation = nationRepository.save(Nation.builder()
                .name("한국")
                .continent(Continent.ASIA)
                .build());

        for (int i = 0; i < 15; i++) {
            reviewRepository.save(createReview(nation.getId()));
        }

        // when
        PageData<Review> reviewsAtPage = reviewRepository.findReviewsAtPage(nation.getId(), 1, 10);

        // then
        assertThat(reviewsAtPage.getTotalPages()).isEqualTo(2);
        assertThat(reviewsAtPage.getPage()).isEqualTo(1);
        assertThat(reviewsAtPage.getData()).hasSize(10);
        assertThat(reviewsAtPage.getTotalElements()).isEqualTo(15);
    }


    private Review createReview(long nationId) {
        return Review.builder()
                .nationId(nationId)
                .userId(1L)
                .cleanliness(10)
                .safety(10)
                .food(10)
                .attraction(10)
                .build();
    }

}