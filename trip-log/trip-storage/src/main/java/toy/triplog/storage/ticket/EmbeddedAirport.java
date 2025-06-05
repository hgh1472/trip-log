package toy.triplog.storage.ticket;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import toy.triplog.domain.ticket.Airport;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EmbeddedAirport {

    private Long nationId;

    private String name;

    public Airport toDomain() {
        return new Airport(nationId, name);
    }

    public static EmbeddedAirport from(Airport airport) {
        return new EmbeddedAirport(airport.getNationId(), airport.getAirportName());
    }
}
