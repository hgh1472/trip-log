package toy.triplog.storage.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.triplog.domain.ticket.Ticket;
import toy.triplog.domain.ticket.TicketRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final JpaTicketRepository jpaTicketRepository;

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity save = jpaTicketRepository.save(TicketEntity.from(ticket));
        return save.toDomain();
    }

    @Override
    public Ticket findTicketWithLock(Long ticketId) {
        TicketEntity ticketEntity = jpaTicketRepository.findTicketEntityByIdWithLock(ticketId);
        return ticketEntity.toDomain();
    }

    @Override
    public void changeTicketStatus(Ticket ticket) {
        jpaTicketRepository.changeStatus(ticket.getTicketStatus(), ticket.getId());
    }

    @Override
    public List<Ticket> findTickets(Long eventId) {
        return jpaTicketRepository.findAllByEventId(eventId)
                .stream()
                .map(TicketEntity::toDomain)
                .toList();
    }

}
