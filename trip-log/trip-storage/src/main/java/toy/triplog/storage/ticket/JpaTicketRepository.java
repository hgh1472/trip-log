package toy.triplog.storage.ticket;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import toy.triplog.domain.ticket.TicketStatus;

import java.util.List;

public interface JpaTicketRepository extends JpaRepository<TicketEntity, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from TicketEntity t where t.id = :ticketId")
    TicketEntity findTicketEntityByIdWithLock(Long ticketId);

    @Modifying
    @Query("update TicketEntity t set t.ticketStatus = :ticketStatus where t.id = :ticketId")
    void changeStatus(TicketStatus ticketStatus, Long ticketId);

    List<TicketEntity> findAllByEventId(Long eventId);
}
