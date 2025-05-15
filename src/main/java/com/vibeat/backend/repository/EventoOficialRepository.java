package com.vibeat.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vibeat.backend.model.EventoOficial;

@Repository
public interface EventoOficialRepository extends JpaRepository<EventoOficial, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
