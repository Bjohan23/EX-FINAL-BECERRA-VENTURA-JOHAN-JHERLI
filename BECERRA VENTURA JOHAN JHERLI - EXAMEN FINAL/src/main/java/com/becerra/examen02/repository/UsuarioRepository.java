package com.becerra.examen02.repository;

import com.becerra.examen02.entity.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByEmailContaining(String email, Pageable pageable);
    Optional<Usuario> findByEmail(String email);
    // Elimina findByUsername ya que el email es el username en tu implementación
}