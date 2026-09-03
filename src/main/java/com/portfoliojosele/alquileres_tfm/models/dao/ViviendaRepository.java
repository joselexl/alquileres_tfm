package com.portfoliojosele.alquileres_tfm.models.dao;
import org.springframework.data.repository.CrudRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Vivienda;

//Extiendo de CrudRepository para poder tener los metetodos de Spring Boot usando la entidad Vivienda
public interface ViviendaRepository extends CrudRepository<Vivienda, Long>{

    // Spring Boot ya nos crea todos los métodos de base de datos por detrás.
}
