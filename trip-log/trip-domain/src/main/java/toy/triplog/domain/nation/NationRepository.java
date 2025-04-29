package toy.triplog.domain.nation;

import java.util.Optional;

public interface NationRepository {

    Optional<Nation> findById(Long id);

    Nation save(Nation nation);
}
