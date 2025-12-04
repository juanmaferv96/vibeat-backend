package com.vibeat.backend.repository;

import com.vibeat.backend.model.EntradaOficial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntradaOficialRepository extends JpaRepository<EntradaOficial, Long> {

    Optional<EntradaOficial> findByCodigoQr(String codigoQr);

    Optional<EntradaOficial> findByReferencia(String referencia);

    List<EntradaOficial> findByUsuarioId(Long usuarioId);

    List<EntradaOficial> findByEventoId(Long eventoId);

    boolean existsByCodigoQr(String codigoQr);

    boolean existsByReferencia(String referencia);
}
