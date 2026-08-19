package com.portfoliojosele.alquileres_tfm.models.services;

import java.util.List;
import com.portfoliojosele.alquileres_tfm.models.entity.Inquilino;

public interface InquilinoService {
    
    public List<Inquilino> findAll();
    public void save(Inquilino inquilino);
    public Inquilino findOne(Long id);
    public void delete(Long id);
    
    
}