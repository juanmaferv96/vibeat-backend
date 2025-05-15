package com.vibeat.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vibeat.backend.model.EventoNoOficial;

@Repository
public interface EventoNoOficialRepository extends JpaRepository<EventoNoOficial, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
