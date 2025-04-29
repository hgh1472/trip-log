package toy.triplog.domain.nation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NationAppender {

    private final NationRepository nationRepository;

}
