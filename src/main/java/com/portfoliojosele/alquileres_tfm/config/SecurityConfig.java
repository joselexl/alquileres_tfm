package com.portfoliojosele.alquileres_tfm.config;

import org.springframework.boot.CommandLineRunner;
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

import com.portfoliojosele.alquileres_tfm.models.dao.UsuarioRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Usuario;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Rutas protegidas
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/img/**", "/layout/**").permitAll() 
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login") // Le decimos cuál es nuestra ruta HTML
                .defaultSuccessUrl("/", true) // Redirige a la raíz al acertar
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            );
        return http.build();
    }
    // Encriptador
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Comprobamos si el usuario admin ya existe para no duplicarlo cada vez que arrancamos
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                // Aquí Spring hace el trabajo sucio de encriptar por nosotros
                admin.setPassword(passwordEncoder.encode("admin123")); 
                admin.setRol("ROLE_ADMIN");
                
                usuarioRepository.save(admin);
                System.out.println("Usuario 'admin' generado automáticamente en la Base de Datos.");
            }
        };
        }
    // Creo el administrador en memoria
    /** 
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
            .username("admin")
            // Encriptador real para guardar la contraseña de forma segura
            .password(passwordEncoder().encode("admin123")) 
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(admin);
    }
    **/

}