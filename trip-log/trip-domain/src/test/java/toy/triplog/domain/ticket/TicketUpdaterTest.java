package toy.triplog.domain.ticket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.triplog.DomainTestContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TicketUpdaterTest extends DomainTestContext {

    @Autowired
    private TicketUpdater ticketUpdater;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserTicketRepository userTicketRepository;

    @DisplayName("티켓을 예약한다.")
    @Test
    void bookTicket() {
        // given
        Airport departure = createAirport(1L, "ICN");
        Airport arrival = createAirport(2L, "KIX");
        LocalDateTime departureTime = LocalDateTime.of(2025, 5, 1, 12, 0);
        LocalDateTime arrivalTime = LocalDateTime.of(2025, 5, 1, 14, 40);
        Ticket ticket = createTicket(departure, arrival, departureTime, arrivalTime, TicketStatus.AVAILABLE, "A01");
        Ticket savedTicket = ticketRepository.save(ticket);

        long userId = 1L;
        // when
        ticketUpdater.bookTicket(savedTicket, userId);

        // then
        List<UserTicket> userTickets = userTicketRepository.findUserTickets(userId);
        assertThat(userTickets).hasSize(1);
        assertThat(userTickets.getFirst().getId()).isNotNull();
        assertThat(userTickets.getFirst().getTicketId()).isEqualTo(savedTicket.getId());
        assertThat(userTickets.getFirst().getUserId()).isEqualTo(userId);
    }

    private static Airport createAirport(long nationId, String airportName) {
        return Airport.builder()
                .nationId(nationId)
                .airportName(airportName)
                .build();
    }

    private static Ticket createTicket(Airport departure, Airport arrival, LocalDateTime departureTime, LocalDateTime arrivalTime, TicketStatus ticketStatus, String seatNumber) {
        return Ticket.builder()
                .eventId(1L)
                .departure(departure)
                .departureTime(departureTime)
                .arrival(arrival)
                .arrivalTime(arrivalTime)
                .seatNumber(seatNumber)
                .ticketStatus(ticketStatus)
                .build();
    }


}
