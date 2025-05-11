package es.iesclaradelrey.da2d1e2425.shopvictorialuis.config;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.filters.JwtAuthenticationFilter;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(200)
public class AppApiSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain appApiSecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
//        http.securityMatcher("/api/app/**");
//
//        // Quitar protección CSRF porque en API no se usan formularios.
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        // Quitar estado
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        // Quitar otras autenticaciones distintas de JWT
//        http.formLogin(AbstractHttpConfigurer::disable);
//        http.httpBasic(AbstractHttpConfigurer::disable);
//
//        // Definir que paths de la api tienen que estar protegidos y cuales no
//        http
//                .authorizeHttpRequests(auth ->
//                        auth
////                                todo remove. Esta linea es para las pruebas en android
//                                .requestMatchers("/api/app/v1/**").permitAll()
//                                // Flujo: importante a la hora de aplicar las reglas de autorización
//                                .requestMatchers("/api/app/v1/auth/**").permitAll()
//                                .anyRequest().authenticated());
//
//
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }


}
