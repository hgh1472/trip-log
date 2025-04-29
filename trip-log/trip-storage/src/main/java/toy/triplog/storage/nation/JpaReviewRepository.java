package toy.triplog.storage.nation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByNationId(Long nationId);

    Page<ReviewEntity> findByNationId(Long nationId, Pageable pageable);

}
