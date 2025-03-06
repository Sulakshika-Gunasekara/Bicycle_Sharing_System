package dev.mybike.mybike.security;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Public endpoints for login/register
                        .requestMatchers("/api/bike/admin/**").hasAuthority("ADMIN") // Admin-only
                        .requestMatchers("/api/bike/rider/**").hasAuthority("RIDER") // User-only
                        .requestMatchers("/api/bike/any/**").hasAnyAuthority("ADMIN", "RIDER") // Admin and Rider
                        .requestMatchers("/api/docking-station/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/docking-station/rider/**").hasAuthority("RIDER")
                        .requestMatchers("/api/docking-station/any/**").hasAnyAuthority("ADMIN", "RIDER")
                        .requestMatchers("/weather/**").permitAll()
                        .requestMatchers("/feedback/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/trips/start").hasAuthority("RIDER")
                        .requestMatchers("/api/trips/end").hasAuthority("RIDER")
                        .requestMatchers("/feedback/user/**").hasAuthority("RIDER")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
