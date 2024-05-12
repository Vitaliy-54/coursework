package by.vstu.restapinews.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/registration") .permitAll()
                        .requestMatchers("/", "/logout", "/confirm-logout", "/logo.png", "/theme/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().hasRole("ADMIN"))
                .formLogin(formLogin -> formLogin
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                         .permitAll()
        );
        return http.build();
    }
}