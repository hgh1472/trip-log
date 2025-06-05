package toy.triplog.domain.ticket;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class Airport {
    private final Long nationId;
    private final String airportName;


}
