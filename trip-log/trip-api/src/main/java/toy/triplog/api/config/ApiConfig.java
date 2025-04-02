package toy.triplog.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"toy.triplog.domain", "toy.triplog.security", "toy.triplog.storage"})
public class ApiConfig {
}
