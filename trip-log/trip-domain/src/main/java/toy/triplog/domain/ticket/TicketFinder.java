package toy.triplog.domain.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketFinder {

    private final TicketRepository ticketRepository;

    public Ticket findTicketExclusively(Long ticketId) {
        return ticketRepository.findTicketWithLock(ticketId);
    }

    public List<Ticket> findTickets(Long eventId) {
        return ticketRepository.findTickets(eventId);
    }
}
