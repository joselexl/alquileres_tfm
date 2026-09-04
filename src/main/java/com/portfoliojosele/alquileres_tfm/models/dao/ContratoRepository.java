package com.portfoliojosele.alquileres_tfm.models.dao;
import org.springframework.data.repository.CrudRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Contrato;



public interface ContratoRepository extends CrudRepository<Contrato, Long> {
    
    // Al dejarlo vacío pero heredando de CrudRepository, 
    // Spring Boot ya nos crea todos los métodos de base de datos por detrás.
    
}