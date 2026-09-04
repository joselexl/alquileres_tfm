package com.portfoliojosele.alquileres_tfm.models.services;
import java.util.List;
import com.portfoliojosele.alquileres_tfm.models.entity.Contrato;



public interface ContratoService {

    public List<Contrato> findAll();
    public void save(Contrato contrato);
    public Contrato findOne(Long id);
    public void delete(Long id);

}
