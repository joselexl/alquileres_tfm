package com.portfoliojosele.alquileres_tfm.models.services;
import java.util.List;
import com.portfoliojosele.alquileres_tfm.models.entity.Fianza;



public interface FianzaService {

    public List<Fianza> findAll();
    public void save(Fianza fianza);
    public Fianza findOne(Long id);
    public void delete(Long id);

}