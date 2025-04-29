package toy.triplog.domain.nation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Nation {

    private final Long id;

    private final String name;

    private final Continent continent;

}
