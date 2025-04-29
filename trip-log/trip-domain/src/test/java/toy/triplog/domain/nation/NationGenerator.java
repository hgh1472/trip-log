package toy.triplog.domain.nation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NationGenerator {

    @Autowired
    private NationRepository nationRepository;

    public Nation generateNation(String name, Continent continent) {
        return nationRepository.save(Nation.builder()
                .name(name)
                .continent(continent)
                .build()
        );
    }

}
