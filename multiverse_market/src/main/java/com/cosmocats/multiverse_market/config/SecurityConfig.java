package com.cosmocats.multiverse_market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Вимикаємо CSRF, щоб Postman міг відправляти POST/PUT/DELETE запити без токенів
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(authz -> authz
                        // Дозволяємо доступ до Swagger UI та документації
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        // Дозволяємо доступ до всіх API endpoint-ів
                        .requestMatchers("/api/**").permitAll()
                        // Дозволяємо головну сторінку
                        .requestMatchers("/").permitAll()
                        // Все інше теж дозволяємо (для простоти лабораторної)
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.permitAll())
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}