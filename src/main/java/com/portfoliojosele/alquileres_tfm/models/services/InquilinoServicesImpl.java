package com.portfoliojosele.alquileres_tfm.models.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.portfoliojosele.alquileres_tfm.models.dao.InquilinoRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Inquilino;

@Service
public class InquilinoServicesImpl implements InquilinoService {

    
    private final InquilinoRepository inquilinoDao;

    public InquilinoServicesImpl(InquilinoRepository inquilinoDao) {
        this.inquilinoDao = inquilinoDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inquilino> findAll() {
        // Le pedimos al repositorio de MySQL que nos devuelva todos los inquilinos
        return (List<Inquilino>) inquilinoDao.findAll();
    }

    @Override
    @Transactional
    public void save(Inquilino inquilino) {
        inquilinoDao.save(inquilino);
    }

    @Override
    @Transactional(readOnly = true)
    public Inquilino findOne(Long id) {
        return inquilinoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        inquilinoDao.deleteById(id);
    }
}