package toy.triplog.domain.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TicketUpdater {

    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;

    @Transactional
    public void bookTicket(Ticket ticket, Long userId) {
        UserTicket userTicket = ticket.booked(userId);
        ticketRepository.changeTicketStatus(ticket);
        userTicketRepository.save(userTicket);
    }

}
