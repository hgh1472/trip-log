package toy.triplog.domain.nation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewFinder {

    private final ReviewRepository reviewRepository;

    public AverageScore findAverageScore(Long nationId) {
        Reviews reviews = reviewRepository.findReviews(nationId);
        return reviews.calculateAverageScore();
    }

    public PageData<Review> findReviewsAtPage(Long nationId, int page, int size) {
        return reviewRepository.findReviewsAtPage(nationId, page, size);
    }

}
