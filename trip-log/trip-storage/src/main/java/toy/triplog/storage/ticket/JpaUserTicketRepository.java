package toy.triplog.storage.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaUserTicketRepository extends JpaRepository<UserTicketEntity, Long> {

    List<UserTicketEntity> findByUserId(Long userId);

}
