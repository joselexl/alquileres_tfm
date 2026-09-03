package com.portfoliojosele.alquileres_tfm.models.services;
import java.util.List;
import com.portfoliojosele.alquileres_tfm.models.entity.Vivienda;

// Uso una interfaz como patrón de diseño estandar en spring boot al usarla como un contrato
public interface ViviendaService {

    public List<Vivienda> findAll();
    public void save(Vivienda vivienda);
    public Vivienda findOne(Long id);
    public void delete(Long id);

}
