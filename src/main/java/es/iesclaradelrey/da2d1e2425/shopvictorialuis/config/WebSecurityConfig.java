package es.iesclaradelrey.da2d1e2425.shopvictorialuis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(300)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {

        http.
                authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/login").permitAll()
//                                .requestMatchers("/")
                                .anyRequest().authenticated());

        //http.formLogin(Customizer.withDefaults());
        http.formLogin(forms -> forms.loginPage("/login") );
        return http.build();
    }


}
