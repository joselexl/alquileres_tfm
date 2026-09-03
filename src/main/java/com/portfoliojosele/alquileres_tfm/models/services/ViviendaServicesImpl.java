package com.portfoliojosele.alquileres_tfm.models.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfoliojosele.alquileres_tfm.models.dao.ViviendaRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Vivienda;


@Service
public class ViviendaServicesImpl implements ViviendaService{

    // Aqui hago la inyección de dependencias no uso autowired
    private final ViviendaRepository viviendaDao;

    public ViviendaServicesImpl(ViviendaRepository viviendaDao) {
        this.viviendaDao = viviendaDao;
    }

    // Esta es la transacción de lectura de vivienda
    @Override
    @Transactional(readOnly = true)
    public List<Vivienda> findAll() {
        // Le pedimos al repositorio de MySQL que nos devuelva todas las viviendas
        return (List<Vivienda>) viviendaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vivienda findOne(Long id) {
        return viviendaDao.findById(id).orElse(null);
    }

    // Esta es la transacción de escritura
    @Override
    @Transactional
    public void save(Vivienda vivienda) {
        viviendaDao.save(vivienda);
    }   

    // Esta es la transacción de borrado
    @Override
    @Transactional
    public void delete(Long id) {
        viviendaDao.deleteById(id);
    }
     
}
