package toy.triplog.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import toy.triplog.domain.nation.Nation;
import toy.triplog.domain.nation.NationService;
import toy.triplog.domain.nation.NationWithScore;

@RestController
@RequiredArgsConstructor
public class NationController {

    private final NationService nationService;

    @GetMapping("/nations/{nationId}")
    public ApiResponse<NationResponse> getNation(@PathVariable long nationId) {
        Nation nation = nationService.findNation(nationId);
        return ApiResponse.ok(NationResponse.of(nation));
    }

    @GetMapping("/nations/{nationId}/score")
    public ApiResponse<NationScoreResponse> getNationWithAverageScore(@PathVariable long nationId) {
        NationWithScore nationWithScore = nationService.findNationWithScore(nationId);
        NationResponse nation = NationResponse.of(nationWithScore.getNation());
        ScoreResponse scoreResponse = ScoreResponse.from(nationWithScore.getAverageScore());
        return ApiResponse.ok(new NationScoreResponse(nation, scoreResponse));
    }

}
