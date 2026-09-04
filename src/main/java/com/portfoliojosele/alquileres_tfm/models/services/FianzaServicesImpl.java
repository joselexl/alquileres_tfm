package com.portfoliojosele.alquileres_tfm.models.services;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.portfoliojosele.alquileres_tfm.models.dao.FianzaRepository;
import com.portfoliojosele.alquileres_tfm.models.entity.Fianza;



@Service
public class FianzaServicesImpl implements FianzaService {

 private final FianzaRepository fianzaDao;

    public FianzaServicesImpl(FianzaRepository fianzaDao) {
        this.fianzaDao = fianzaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Fianza> findAll() {
        // Le pedimos al repositorio de MySQL que nos devuelva todos las fianzas
        return (List<Fianza>) fianzaDao.findAll();
    }

    @Override
    @Transactional
    public void save(Fianza fianza) {
        fianzaDao.save(fianza);
    }

    @Override
    @Transactional(readOnly = true)
    public Fianza findOne(Long id) {
        return fianzaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        fianzaDao.deleteById(id);
    }

}