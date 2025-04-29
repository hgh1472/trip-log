package toy.triplog.domain.nation;

import lombok.Getter;

@Getter
public class AverageScore {

    private double total;

    private double food;

    private double cleanliness;

    private double safety;

    private double attraction;

    private AverageScore(double total, double food, double cleanliness, double safety, double attraction) {
        this.total = total;
        this.food = food;
        this.cleanliness = cleanliness;
        this.safety = safety;
        this.attraction = attraction;
    }

    public static AverageScore of(double food, double cleanliness, double safety, double attraction) {
        double total = (food + cleanliness + safety + attraction) / 4;
        return new AverageScore(total, food, cleanliness, safety, attraction);
    }

}
