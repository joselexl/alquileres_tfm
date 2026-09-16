package com.portfoliojosele.alquileres_tfm.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.portfoliojosele.alquileres_tfm.models.dao.UsuarioRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Usuario;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    // Configuración de las rutas y permisos
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Dejamos pasar sin login a todo lo visual (CSS, JS, imágenes...) 
                // Si no hacemos esto, la pantalla de login se vería rota y sin estilos
                .requestMatchers("/css/**", "/js/**", "/img/**", "/layout/**").permitAll() 
                
                // Aquí cortamos el grifo: a la gestión de usuarios solo entra el ADMIN.
                // Si un usuario normal intenta colarse por URL, Spring le tira un error 403
                .requestMatchers("/usuarios/**").hasAuthority("ROLE_ADMIN")
                
                // Para el resto de cosas de la app (viviendas, inquilinos...), con estar logueado nos sirve
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                // Enganchamos nuestra propia pantalla de login (para no usar la genérica de Spring)
                .loginPage("/login") 
                // Si mete bien la contraseña, lo mandamos directo a la página principal
                .defaultSuccessUrl("/", true) 
                .permitAll()
            )
            .logout(logout -> logout
                // Dejamos que cualquiera pueda darle al botón de cerrar sesión
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