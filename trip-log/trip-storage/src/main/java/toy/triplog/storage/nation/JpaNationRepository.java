package toy.triplog.storage.nation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNationRepository extends JpaRepository<NationEntity, Long> {
}
