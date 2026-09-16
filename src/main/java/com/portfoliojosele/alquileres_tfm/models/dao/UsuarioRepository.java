package com.portfoliojosele.alquileres_tfm.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfoliojosele.alquileres_tfm.models.entity.Usuario;

// Repositorio para la gestión de la entidad Usuario en base de datos.
@Repository  
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // "SELECT * FROM usuarios WHERE username = ?" por si no existe en la base de datos el usuario   
    Optional<Usuario> findByUsername(String username);
}