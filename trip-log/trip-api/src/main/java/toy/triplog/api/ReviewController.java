package toy.triplog.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import toy.triplog.domain.auth.ApiUser;
import toy.triplog.domain.nation.PageData;
import toy.triplog.domain.nation.Review;
import toy.triplog.domain.nation.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/nations/{nationId}/reviews")
    public ApiResponse<ReviewResponse> writeReview(@PathVariable Long nationId, @RequestBody ReviewRequest reviewRequest, @AuthenticationPrincipal ApiUser apiUser) {
        Review review = reviewService.addReview(reviewRequest.toWrittenReview(nationId, apiUser));
        return ApiResponse.ok(ReviewResponse.from(review));
    }

    @GetMapping("/nations/{nationId}/reviews")
    public ApiResponse<PageData<ReviewResponse>> getReviews(@PathVariable Long nationId, @RequestParam int page) {
        PageData<Review> reviews = reviewService.findReviewsAtPage(nationId, page);
        List<ReviewResponse> reviewResponses = reviews.getData().stream()
                .map(ReviewResponse::from)
                .toList();
        return ApiResponse.ok(new PageData<>(reviewResponses, reviews.getPage(), reviews.getTotalPages(), reviews.getTotalElements()));
    }

}
