package adrianobruzzese.bem5w3d5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Disabilita il form di login predefinito
        httpSecurity.formLogin().disable();

        // Disabilita la protezione CSRF
        httpSecurity.csrf().disable();

        // Configura la gestione delle sessioni come stateless
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Configura le autorizzazioni per gli endpoint
        httpSecurity.authorizeRequests().antMatchers("/auth/**").permitAll().anyRequest().authenticated();

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // Utilizzo BCrypt con forza 11
        return new BCryptPasswordEncoder(11);
    }
}
