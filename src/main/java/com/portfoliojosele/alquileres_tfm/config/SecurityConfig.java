package com.portfoliojosele.alquileres_tfm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. EL ESCUDO: Configuramos qué rutas están protegidas
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/img/**", "/layout/**").permitAll() 
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .defaultSuccessUrl("/", true) // Redirige a la raíz al acertar
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            );

        return http.build();
    }

    // 2. EL ENCRIPTADOR (Súper profesional para el TFM)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. EL USUARIO: Creamos el administrador en memoria
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
            .username("admin")
            // Usamos el encriptador real para guardar la contraseña de forma segura
            .password(passwordEncoder().encode("admin123")) 
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(admin);
    }
}