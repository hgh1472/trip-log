package toy.triplog.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TripLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripLogApplication.class, args);
    }

}
