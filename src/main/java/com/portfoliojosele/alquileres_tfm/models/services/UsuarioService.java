package com.portfoliojosele.alquileres_tfm.models.services;

import java.util.List;
import com.portfoliojosele.alquileres_tfm.models.entity.Usuario;

public interface UsuarioService {
    
    // Método para sacar todos los usuarios 
    public List<Usuario> findAll();
    
    // Método para guardar un usuario nuevo en MySQL
    public void save(Usuario usuario);

    //Para buscar un usuario antes de editarlo
    public Usuario findById(Long id);

    //Para fulminarlo de la base de datos
    public void delete(Long id);
    
}