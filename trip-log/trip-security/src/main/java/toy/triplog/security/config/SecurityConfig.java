package toy.triplog.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import toy.triplog.domain.auth.TokenResolver;
import toy.triplog.security.JwtFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenResolver tokenResolver;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtFilter(tokenResolver), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/sign-up").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/docs/index.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/nations/{nationId}/reviews").authenticated()
                        .requestMatchers(HttpMethod.GET, "/nations/{nationId}/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET, "/nations/{nationId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/nations/{nationId}/score").permitAll()

                        .requestMatchers("/event/{eventId}/waiting").authenticated()
                        .requestMatchers("/event/{eventId}").authenticated()
                )
                .build();
    }

}
