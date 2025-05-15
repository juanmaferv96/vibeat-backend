package com.vibeat.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vibeat.backend.model.EntradaNoOficial;

@Repository
public interface EntradaNoOficialRepository extends JpaRepository<EntradaNoOficial, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
