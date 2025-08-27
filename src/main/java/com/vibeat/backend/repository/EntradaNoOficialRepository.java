package com.vibeat.backend.repository;

import com.vibeat.backend.model.EntradaNoOficial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntradaNoOficialRepository extends JpaRepository<EntradaNoOficial, Long> {

    Optional<EntradaNoOficial> findByCodigoQr(String codigoQr);

    Optional<EntradaNoOficial> findByReferencia(String referencia);

    List<EntradaNoOficial> findByUsuarioId(Long usuarioId);

    List<EntradaNoOficial> findByEventoId(Long eventoId);

    boolean existsByCodigoQr(String codigoQr);

    boolean existsByReferencia(String referencia);
}
