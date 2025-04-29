package toy.triplog.domain.nation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class NationServiceTest {

    @InjectMocks
    private NationService nationService;
    @Mock
    private NationFinder nationFinder;

    @DisplayName("나라를 조회한다.")
    @Test
    void findNation() {
        // given
        Long nationId = 1L;

        // when
        nationService.findNation(nationId);

        // then
        verify(nationFinder, Mockito.times(1)).findNation(nationId);
    }

    @DisplayName("나라를 평균 점수와 함께 조회한다.")
    @Test
    void findNationWithScore() {
        // given
        Long nationId = 1L;

        // when
        nationService.findNationWithScore(nationId);

        // then
        verify(nationFinder, Mockito.times(1)).findNationWithScore(nationId);
    }

}