package com.portfoliojosele.alquileres_tfm.models.dao;
import org.springframework.data.repository.CrudRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Fianza;



public interface FianzaRepository extends CrudRepository<Fianza, Long> {
    
    // Al dejarlo vacío pero heredando de CrudRepository, 
    // Spring Boot ya nos crea todos los métodos de base de datos por detrás.
    
}