package toy.triplog.domain.nation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewAppender reviewAppender;
    private final ReviewFinder reviewFinder;

    @Transactional
    public Review addReview(WrittenReview writtenReview) {
        return reviewAppender.appendReview(writtenReview);
    }

    @Transactional(readOnly = true)
    public PageData<Review> findReviewsAtPage(Long nationId, int page) {
        return reviewFinder.findReviewsAtPage(nationId, page, 10);
    }

}
