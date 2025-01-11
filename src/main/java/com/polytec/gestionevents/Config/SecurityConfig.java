package com.polytec.gestionevents.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/participants/**").hasRole("USER")
                        .requestMatchers("/api/events/**", "/api/venues/**", "/api/managers/**").hasRole("ADMIN")
                        .requestMatchers("/api/events/**").hasRole("MANAGER")
                        .anyRequest().authenticated()
                )
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123")) // Hachage du mot de passe
                .roles("ADMIN")
                .build();

        UserDetails manager = User.builder()
                .username("manager")
                .password(passwordEncoder.encode("manager123")) // Hachage du mot de passe
                .roles("MANAGER")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123")) // Hachage du mot de passe
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, manager, user);
    }
}
