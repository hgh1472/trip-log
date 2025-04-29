package toy.triplog.storage.nation;

import jakarta.persistence.*;
import lombok.*;
import toy.triplog.domain.nation.Continent;
import toy.triplog.domain.nation.Nation;

@Entity
@Table(name = "nation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class NationEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Continent continent;

    public static NationEntity from(Nation nation) {
        return NationEntity.builder()
                .id(nation.getId())
                .name(nation.getName())
                .continent(nation.getContinent())
                .build();
    }

    public Nation toNation() {
        return Nation.builder()
                .id(this.id)
                .name(this.name)
                .continent(this.continent)
                .build();
    }

}
