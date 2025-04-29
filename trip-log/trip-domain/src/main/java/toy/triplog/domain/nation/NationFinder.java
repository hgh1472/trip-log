package toy.triplog.domain.nation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NationFinder {

    private final NationRepository nationRepository;

    private final ReviewFinder reviewFinder;

    public Nation findNation(Long id) {
        return nationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 국가입니다."));
    }

    public NationWithScore findNationWithScore(Long id) {
        Nation nation = nationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 국가입니다."));
        AverageScore averageScore = reviewFinder.findAverageScore(id);
        return NationWithScore.of(nation, averageScore);
    }

}
