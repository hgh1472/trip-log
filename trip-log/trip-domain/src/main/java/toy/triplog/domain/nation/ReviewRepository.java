package toy.triplog.domain.nation;

import java.util.Optional;

public interface ReviewRepository {

    Review save(Review review);

    Optional<Review> findById(Long id);

    Reviews findReviews(Long nationId);

    PageData<Review> findReviewsAtPage(Long nationId, int page, int size);

}
