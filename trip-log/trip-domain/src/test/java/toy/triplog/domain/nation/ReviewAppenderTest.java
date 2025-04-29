package toy.triplog.domain.nation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.triplog.domain.DomainTestContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class ReviewAppenderTest extends DomainTestContext {

    @Autowired
    private ReviewAppender reviewAppender;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private NationGenerator nationGenerator;


    @DisplayName("리뷰를 추가한다.")
    @Test
    void appendReview() {
        // given
        Nation nation = nationGenerator.generateNation("한국", Continent.ASIA);
        WrittenReview writtenReview = new WrittenReview(nation.getId(), 1, 5, 5, 5, 5);

        // when
        Review appendedReview = reviewAppender.appendReview(writtenReview);

        // then
        Optional<Review> findReview = reviewRepository.findById(appendedReview.getId());
        assertThat(findReview).isPresent();
    }

}