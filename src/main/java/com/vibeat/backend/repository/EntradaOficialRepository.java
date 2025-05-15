package com.vibeat.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vibeat.backend.model.EntradaOficial;

@Repository
public interface EntradaOficialRepository extends JpaRepository<EntradaOficial, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
