package com.portfoliojosele.alquileres_tfm.models.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfoliojosele.alquileres_tfm.models.dao.UsuarioRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Usuario;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Obtengo el repositorio para poder hablar con MySQL
    private final UsuarioRepository usuarioRepository;
    // Inyecto el encriptador de contraseñas. 
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
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
    @Transactional
    public void save(Usuario usuario) {
        // Obtengo la contraseña normal que metieron en el formulario
        String passwordPlana = usuario.getPassword();
        
        // La envío por BCrypt para encriptarla
        String passwordEncriptada = passwordEncoder.encode(passwordPlana);
        
        // Se la vuelvo a enviar al objeto usuario ya encriptada
        usuario.setPassword(passwordEncriptada);
        
        // Guardo en la base de datos
        usuarioRepository.save(usuario);
    }
}