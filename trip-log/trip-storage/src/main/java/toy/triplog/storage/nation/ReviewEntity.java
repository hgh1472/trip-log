package toy.triplog.storage.nation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import toy.triplog.domain.nation.Review;

@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long nationId;

    private Long userId;

    private int food;

    private int cleanliness;

    private int safety;

    private int attraction;

    public static ReviewEntity from(Review review) {
        return new ReviewEntity(
                review.getId(),
                review.getNationId(),
                review.getUserId(),
                review.getFood(),
                review.getCleanliness(),
                review.getSafety(),
                review.getAttraction()
        );
    }

    public Review toReview() {
        return new Review(
                this.id,
                this.nationId,
                this.userId,
                this.food,
                this.cleanliness,
                this.safety,
                this.attraction
        );
    }

}
