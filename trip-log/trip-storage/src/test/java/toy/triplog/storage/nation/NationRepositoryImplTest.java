package toy.triplog.storage.nation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.triplog.domain.nation.Continent;
import toy.triplog.domain.nation.Nation;
import toy.triplog.domain.nation.NationRepository;
import toy.triplog.storage.StorageContextTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class NationRepositoryImplTest extends StorageContextTest {

    @Autowired
    private NationRepository nationRepository;

    private Nation nation;

    @BeforeEach
    void setUp() {
        nation = Nation.builder()
                .name("한국")
                .continent(Continent.ASIA)
                .build();

    }

    @DisplayName("ID를 통해 나라를 DB로부터 조회한다.")
    @Test
    void findById() {
        // given
        Nation saved = nationRepository.save(nation);

        // when
        Optional<Nation> find = nationRepository.findById(saved.getId());

        // then
        assertThat(find).isPresent();
    }

    @DisplayName("DB에 나라를 저장한다.")
    @Test
    void save() {
        // when
        Nation saved = nationRepository.save(nation);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved)
                .extracting("name", "continent")
                .contains(nation.getName(), nation.getContinent());
    }

}