package toy.triplog.domain.nation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Review {

    private Long id;

    private Long nationId;

    private Long userId;

    private int food;

    private int cleanliness;

    private int safety;

    private int attraction;

    public static Review from(WrittenReview writtenReview) {
        validate(writtenReview.food(), writtenReview.cleanliness(), writtenReview.safety(), writtenReview.attraction());

        return Review.builder()
                .nationId(writtenReview.nationId())
                .userId(writtenReview.userId())
                .food(writtenReview.food())
                .cleanliness(writtenReview.cleanliness())
                .safety(writtenReview.safety())
                .attraction(writtenReview.attraction())
                .build();
    }

    private static void validate(int food, int cleanliness, int safety, int attraction) {
        if (food < 1 || food > 10) {
            throw new IllegalArgumentException("음식 점수는 1점 이상 10점 이하이어야 합니다.");
        }
        if (cleanliness < 1 || cleanliness > 10) {
            throw new IllegalArgumentException("위생 점수는 1점 이상 10점 이하이어야 합니다.");
        }
        if (safety < 1 || safety > 10) {
            throw new IllegalArgumentException("안전 점수는 1점 이상 10점 이하이어야 합니다.");
        }
        if (attraction < 1 || attraction > 10) {
            throw new IllegalArgumentException("놀거리 점수는 1점 이상 10점 이하이어야 합니다.");
        }
    }

}
