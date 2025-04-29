package toy.triplog.storage.nation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import toy.triplog.domain.nation.PageData;
import toy.triplog.domain.nation.Review;
import toy.triplog.domain.nation.ReviewRepository;
import toy.triplog.domain.nation.Reviews;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final JpaReviewRepository jpaReviewRepository;

    @Override
    public Review save(Review review) {
        ReviewEntity save = jpaReviewRepository.save(ReviewEntity.from(review));
        return save.toReview();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return jpaReviewRepository.findById(id).map(ReviewEntity::toReview);
    }


    @Override
    public Reviews findReviews(Long nationId) {
        List<ReviewEntity> reviewEntities = jpaReviewRepository.findByNationId(nationId);
        List<Review> reviews = reviewEntities.stream()
                .map(ReviewEntity::toReview)
                .toList();
        return Reviews.from(reviews);
    }

    @Override
    public PageData<Review> findReviewsAtPage(Long nationId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Review> reviewsAtPage = jpaReviewRepository.findByNationId(nationId, pageRequest).map(ReviewEntity::toReview);
        return new PageData<>(reviewsAtPage.getContent(), page, reviewsAtPage.getTotalPages(), reviewsAtPage.getTotalElements());
    }

}
