package toy.triplog.domain.nation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toy.triplog.domain.auth.ApiUser;

import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;
    @Mock
    private ReviewAppender reviewAppender;

    @DisplayName("작성한 리뷰를 추가한다.")
    @Test
    void addReview() {
        // given
        WrittenReview writtenReview = new WrittenReview(1L, 1, 5, 5, 5, 5);

        // when
        Review review = reviewService.addReview(writtenReview);

        // then
        verify(reviewAppender, times(1)).appendReview(writtenReview);
    }

}