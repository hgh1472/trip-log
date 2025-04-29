package toy.triplog.domain.nation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewAppender {

    private final ReviewRepository reviewRepository;

    public Review appendReview(WrittenReview writtenReview) {
        Review review = Review.from(writtenReview);
        return reviewRepository.save(review);
    }

}
