package toy.triplog.domain.nation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NationService {

    private final NationFinder nationFinder;

    public Nation findNation(Long id) {
        return nationFinder.findNation(id);
    }

    public NationWithScore findNationWithScore(Long id) {
        return nationFinder.findNationWithScore(id);
    }

}
