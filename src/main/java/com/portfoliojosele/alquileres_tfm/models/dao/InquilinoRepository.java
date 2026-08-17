package com.portfoliojosele.alquileres_tfm.models.dao;
import org.springframework.data.repository.CrudRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Inquilino;


public interface InquilinoRepository extends CrudRepository<Inquilino, Long> {
    
    // Al dejarlo vacío pero heredando de CrudRepository, 
    // Spring Boot ya nos crea todos los métodos de base de datos por detrás.
    
}