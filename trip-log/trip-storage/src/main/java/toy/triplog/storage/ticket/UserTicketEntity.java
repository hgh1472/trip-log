package toy.triplog.storage.ticket;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import toy.triplog.domain.ticket.UserTicket;

@Entity
@Table(name = "user_ticket")
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long ticketId;

    public UserTicket toDomain() {
        return UserTicket.builder()
                .id(id)
                .userId(userId)
                .ticketId(ticketId)
                .build();
    }

    public static UserTicketEntity from(UserTicket userTicket) {
        return UserTicketEntity.builder()
                .id(userTicket.getId())
                .userId(userTicket.getUserId())
                .ticketId(userTicket.getTicketId())
                .build();
    }
}
