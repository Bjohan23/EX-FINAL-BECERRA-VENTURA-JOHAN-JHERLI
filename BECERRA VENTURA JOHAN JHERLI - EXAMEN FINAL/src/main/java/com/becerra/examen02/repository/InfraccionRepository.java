package com.becerra.examen02.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.becerra.examen02.entity.Infraccion;

@Repository
public interface InfraccionRepository extends JpaRepository<Infraccion, Integer>{
    List<Infraccion> findByDniContaining(String dni, Pageable pageable);
    List<Infraccion> findByDni(String dni);

}
