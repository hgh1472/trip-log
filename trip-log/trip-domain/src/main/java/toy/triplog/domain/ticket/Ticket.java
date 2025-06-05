package toy.triplog.domain.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Ticket {
    private Long id;

    private Long eventId;

    private Airport departure;

    private Airport arrival;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private String seatNumber;

    private TicketStatus ticketStatus;

    public boolean isNotAvailable() {
        return !ticketStatus.equals(TicketStatus.AVAILABLE);
    }

    public UserTicket booked(Long userId) {
        this.ticketStatus = TicketStatus.BOOKED;
        return UserTicket.createUserTicket(this, userId);
    }

}
