package com.portfoliojosele.alquileres_tfm.models.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfoliojosele.alquileres_tfm.models.dao.UsuarioRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Usuario;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Inyectamos el repositorio y el encriptador por constructor 
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        // Saco todos los usuarios que haya registrados en la tabla
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        // Buscamos al usuario, y si por lo que sea no existe, devolvemos null
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Le pasamos la guadaña al usuario por su ID
        usuarioRepository.deleteById(id);
    }

    // El método de guardado inteligente (crear y editar)
    @Override
    @Transactional
    public void save(Usuario usuario) {
        
        // Si el usuario ya tiene un ID mayor que 0, significa que ESTAMOS EDITANDO
        if (usuario.getId() != null && usuario.getId() > 0) {
            
            // Vamos a MySQL a por los datos viejos de este usuario
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
            
            if (usuarioExistente != null) {
                // Si el campo password viene vacío del formulario (el admin no quiere cambiarla)...
                if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                    // ¡Le respetamos la contraseña encriptada que ya tenía!
                    usuario.setPassword(usuarioExistente.getPassword());
                } else {
                    // Si ha escrito una contraseña nueva, la encriptamos
                    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                }
            }
        } 
        // Si no tiene ID, es un USUARIO NUEVO
        else {
            // Encriptamos la clave sí o sí
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        
        // Finalmente, guardamos o actualizamos en la BD
        usuarioRepository.save(usuario);
    }
}
