package toy.triplog.storage.nation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.triplog.domain.nation.Nation;
import toy.triplog.domain.nation.NationRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class NationRepositoryImpl implements NationRepository {

    private final JpaNationRepository jpaNationRepository;

    @Override
    public Optional<Nation> findById(Long id) {
        return jpaNationRepository.findById(id).map(NationEntity::toNation);
    }

    @Override
    public Nation save(Nation nation) {
        return jpaNationRepository.save(NationEntity.from(nation)).toNation();
    }

}
