package toy.triplog.domain.ticket;

import java.util.List;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    Ticket findTicketWithLock(Long ticketId);

    void changeTicketStatus(Ticket ticket);

    List<Ticket> findTickets(Long eventId);
}
