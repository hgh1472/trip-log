package toy.triplog.storage.nation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.triplog.domain.nation.Continent;
import toy.triplog.domain.nation.Nation;

import static org.assertj.core.api.Assertions.assertThat;

class NationEntityTest {

    @DisplayName("나라 도메인 객체로부터 나라 엔티티로 변환한다.")
    @Test
    void from() {
        // given
        Nation nation = Nation.builder()
                .id(1L)
                .name("한국")
                .continent(Continent.ASIA)
                .build();

        // when
        NationEntity entity = NationEntity.from(nation);

        // then
        assertThat(entity)
                .extracting("id", "name", "continent")
                .contains(nation.getId(), nation.getName(), nation.getContinent());
    }

    @DisplayName("나라 엔티티로부터 나라 도메인으로 변환한다.")
    @Test
    void toNation() {
        // given
        NationEntity entity = NationEntity.builder()
                .id(1L)
                .name("한국")
                .continent(Continent.ASIA)
                .build();

        // when
        Nation nation = entity.toNation();

        // then
        assertThat(nation)
                .extracting("id", "name", "continent")
                .contains(entity.getId(), entity.getName(), entity.getContinent());
    }


}