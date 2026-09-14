package com.portfoliojosele.alquileres_tfm.models.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.portfoliojosele.alquileres_tfm.models.dao.UsuarioRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Usuario;

@Service 
public class UsuarioDetallesService implements UserDetailsService{

private final UsuarioRepository usuarioRepository;

    // Inyección de dependencias por constructor 
    public UsuarioDetallesService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Este método es llamado automáticamente por Spring Security cuando alguien intenta hacer login.
     * @param username El nombre de usuario introducido en el formulario.
     * @return UserDetails El usuario "traducido" al formato que entiende Spring Security.
     * @throws UsernameNotFoundException Si el usuario no existe en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. Buscamos el usuario en nuestra base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario '" + username + "' no existe."));

        // 2. Construimos y devolvemos el objeto que Spring Security sí entiende (UserDetails)
        // Usamos el patrón Builder que nos proporciona la clase User de Spring.
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // Esta contraseña ya debe estar encriptada en la BBDD
                .authorities(usuario.getRol())   // Asignamos el rol (ej. "ROLE_ADMIN")
                .build();
    }

    
}
