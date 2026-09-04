package com.portfoliojosele.alquileres_tfm.models.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfoliojosele.alquileres_tfm.models.dao.ContratoRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Contrato;



@Service
public class ContratoServicesImpl implements ContratoService {

 private final ContratoRepository contratoDao;

    public ContratoServicesImpl(ContratoRepository contratoDao) {
        this.contratoDao = contratoDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> findAll() {
        // Le pedimos al repositorio de MySQL que nos devuelva todos los contratos
        return (List<Contrato>) contratoDao.findAll();
    }

    @Override
    @Transactional
    public void save(Contrato contrato) {
        contratoDao.save(contrato);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrato findOne(Long id) {
        return contratoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        contratoDao.deleteById(id);
    }

}
