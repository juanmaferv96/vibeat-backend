package com.vibeat.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vibeat.backend.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByEmail(String email);
    boolean existsByCif(String cif);
    boolean existsByUser(String user);
}