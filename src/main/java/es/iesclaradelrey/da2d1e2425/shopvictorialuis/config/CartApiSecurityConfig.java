package es.iesclaradelrey.da2d1e2425.shopvictorialuis.config;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(100)
public class CartApiSecurityConfig {
    @Bean
    public SecurityFilterChain cartApiSecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.securityMatcher("/api/**");

        // Quitar protección CSRF porque en API no se usan formularios.
        http.csrf(AbstractHttpConfigurer::disable);

        // todo Con o sin estado?¿ Es con, no?
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Quitar otras autenticaciones distintas de JWT todo ?¿¿?
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // Definir que paths de la api tienen que estar protegidos y cuales no
        http
                .authorizeHttpRequests(auth ->
                        auth
                                // Flujo: importante a la hora de aplicar las reglas de autorización
                                .requestMatchers("/api/**").permitAll()
                                .anyRequest().authenticated());


        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
