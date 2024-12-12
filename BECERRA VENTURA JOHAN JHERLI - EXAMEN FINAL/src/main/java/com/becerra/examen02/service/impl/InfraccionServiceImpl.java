package com.becerra.examen02.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.becerra.examen02.entity.Infraccion;
import com.becerra.examen02.exception.GeneralException;
import com.becerra.examen02.exception.NoDataFoundException;
import com.becerra.examen02.exception.ValidateException;
import com.becerra.examen02.repository.InfraccionRepository;
import com.becerra.examen02.service.InfraccionService;
import com.becerra.examen02.validator.InfraccionValidator;


@Service
public class InfraccionServiceImpl  implements InfraccionService{

    @Autowired
    private InfraccionRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Infraccion> findAll(Pageable page) {
        try {
            List<Infraccion> registros = repository.findAll(page).toList();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidorr");
        }
    }

    @Override
    public List<Infraccion> findAll() {
        try {
            List<Infraccion> registros = repository.findAll();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidorr");
        }
    }

    @Override
    @Transactional(readOnly = true)

    public List<Infraccion> findByNombre(String dni, Pageable page) {
        try {
            List<Infraccion> registros = repository.findByDniContaining(dni, page);
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    public Infraccion findById(int id) {
        try {
            Infraccion registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }
    @Override
    public List<Infraccion> findByDni(String dni) {
        try {
            return repository.findByDni(dni);
        } catch (Exception e) {
            throw new GeneralException("Error al buscar infracciones por DNI");
        }
    }

    @Override
    @Transactional

    public Infraccion save(Infraccion infraccion) {
         try {
            InfraccionValidator.save(infraccion);

            if(infraccion.getId() == 0) {
                Infraccion nuevo = repository.save(infraccion);
                return nuevo;
            }

            Infraccion registro = repository.findById(infraccion.getId())
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
                    registro.setDni(infraccion.getDni());
                    registro.setFecha(infraccion.getFecha());
                    registro.setPlaca(infraccion.getPlaca());
                    registro.setInfracción(infraccion.getInfracción());
                    registro.setDescripcion(infraccion.getDescripcion());
                    repository.save(registro);

            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            Infraccion registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            repository.delete(registro);
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }
    
    
}
