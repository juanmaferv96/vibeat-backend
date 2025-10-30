package com.vibeat.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vibeat.backend.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Unicidades case-insensitive
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByDniIgnoreCase(String dni);
    boolean existsByUserIgnoreCase(String user);

    // Búsqueda para login case-insensitive
    Optional<Usuario> findByUserIgnoreCase(String user);
}
