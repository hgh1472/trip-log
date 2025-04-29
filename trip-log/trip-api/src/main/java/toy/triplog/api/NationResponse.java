package toy.triplog.api;

import toy.triplog.domain.nation.Nation;

public record NationResponse(
        String name,
        String continent
) {
    public static NationResponse of(Nation nation) {
        return new NationResponse(nation.getName(), nation.getContinent().name());
    }
}
