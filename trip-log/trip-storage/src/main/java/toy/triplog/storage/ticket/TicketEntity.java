package toy.triplog.storage.ticket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.triplog.domain.ticket.Ticket;
import toy.triplog.domain.ticket.TicketStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nationId", column = @Column(name = "departure_nation_id")),
            @AttributeOverride(name = "name", column = @Column(name = "departure_name"))
    })
    private EmbeddedAirport departure;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nationId", column = @Column(name = "arrival_nation_id")),
            @AttributeOverride(name = "name", column = @Column(name = "arrival_name"))
    })
    private EmbeddedAirport arrival;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    public Ticket toDomain() {
        return Ticket.builder()
                .id(id)
                .eventId(eventId)
                .departure(departure.toDomain())
                .arrival(arrival.toDomain())
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .seatNumber(seatNumber)
                .ticketStatus(ticketStatus)
                .build();
    }

    public static TicketEntity from(Ticket ticket) {
        return TicketEntity.builder()
                .id(ticket.getId())
                .eventId(ticket.getEventId())
                .departure(EmbeddedAirport.from(ticket.getDeparture()))
                .arrival(EmbeddedAirport.from(ticket.getArrival()))
                .departureTime(ticket.getDepartureTime())
                .arrivalTime(ticket.getArrivalTime())
                .seatNumber(ticket.getSeatNumber())
                .ticketStatus(ticket.getTicketStatus())
                .build();
    }

}
