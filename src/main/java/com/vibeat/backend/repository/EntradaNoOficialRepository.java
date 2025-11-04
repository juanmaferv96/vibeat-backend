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
    // Si la FK a evento es "evento" (objeto) y el id es "id"
    Optional<EntradaNoOficial> findByEventoIdAndCodigoQr(Long eventoId, String codigoQr);

    // Si en tu entidad el campo del código se llama "codigo_qr" con @Column, el atributo Java debería ser "codigoQr".
    // Si tu relación a evento es diferente (ej. eventoNoOficial), cambia "Evento" por el nombre del atributo:
    // Optional<EntradaNoOficial> findByEventoNoOficial_IdAndCodigoQr(Long eventoId, String codigoQr);

}
