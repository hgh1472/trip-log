package toy.triplog.domain.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserTicket {

    private Long id;

    private Long userId;

    private Long ticketId;

    public static UserTicket createUserTicket(Ticket ticket, Long userId) {
        return UserTicket.builder()
                .userId(userId)
                .ticketId(ticket.getId())
                .build();
    }
}
