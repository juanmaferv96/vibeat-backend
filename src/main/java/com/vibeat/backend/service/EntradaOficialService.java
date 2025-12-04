package com.vibeat.backend.service;

import com.vibeat.backend.model.EntradaOficial;
import com.vibeat.backend.repository.EntradaOficialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntradaOficialService {

    @Autowired
    private EntradaOficialRepository entradaOficialRepository;

    @Transactional
    public EntradaOficial saveEntrada(EntradaOficial e) {
        validarRequeridos(e);

        if (e.getPremiada() == null) e.setPremiada(false);
        if (e.getEscaneada() == null) e.setEscaneada(false);

        LocalDateTime now = (e.getFechaCompra() == null) ? EntradaUtils.now() : e.getFechaCompra();
        e.setFechaCompra(now);

        String ts = now.format(EntradaUtils.TS);

        if (e.getCodigoQr() == null || e.getCodigoQr().isBlank()) {
            e.setCodigoQr("O_" + e.getEventoId() + "_" + e.getUsuarioId() + "_" + ts);
        }
        if (e.getReferencia() == null || e.getReferencia().isBlank()) {
            e.setReferencia("O" + e.getEventoId() + e.getUsuarioId() + ts);
        }

        int intentos = 0;
        while (true) {
            try {
                return entradaOficialRepository.save(e);
            } catch (DataIntegrityViolationException ex) {
                if (++intentos > 2) throw ex;
                now = now.plusSeconds(1);
                e.setFechaCompra(now);
                ts = now.format(EntradaUtils.TS);
                e.setCodigoQr("O_" + e.getEventoId() + "_" + e.getUsuarioId() + "_" + ts);
                e.setReferencia("O" + e.getEventoId() + e.getUsuarioId() + ts);
            }
        }
    }

    // ---- NUEVOS: todo por service ----
    @Transactional(readOnly = true)
    public List<EntradaOficial> getAllEntradas() {
        return entradaOficialRepository.findAll();
    }

    @Transactional
    public boolean deleteEntrada(Long id) {
        if (!entradaOficialRepository.existsById(id)) return false;
        entradaOficialRepository.deleteById(id);
        return true;
    }

    // ---- Consultas ----
    @Transactional(readOnly = true)
    public Optional<EntradaOficial> findById(Long id) {
        return entradaOficialRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<EntradaOficial> findByCodigoQr(String codigoQr) {
        return entradaOficialRepository.findByCodigoQr(codigoQr);
    }

    @Transactional(readOnly = true)
    public Optional<EntradaOficial> findByReferencia(String referencia) {
        return entradaOficialRepository.findByReferencia(referencia);
    }

    @Transactional(readOnly = true)
    public List<EntradaOficial> findByUsuarioId(Long usuarioId) {
        return entradaOficialRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<EntradaOficial> findByEventoId(Long eventoId) {
        return entradaOficialRepository.findByEventoId(eventoId);
    }

    // ---- Marcadores ----
    @Transactional
    public void marcarEscaneada(Long id, boolean escaneada) {
        entradaOficialRepository.findById(id).ifPresent(e -> {
            e.setEscaneada(escaneada);
            entradaOficialRepository.save(e);
        });
    }

    @Transactional
    public void marcarPremiada(Long id, boolean premiada, String nombrePremio) {
        entradaOficialRepository.findById(id).ifPresent(e -> {
            e.setPremiada(premiada);
            e.setNombrePremio(premiada ? nombrePremio : null);
            entradaOficialRepository.save(e);
        });
    }

    // ---- Helper ----
    private void validarRequeridos(EntradaOficial e) {
        if (e.getEventoId() == null) throw new IllegalArgumentException("EVENTO_ID es obligatorio");
        if (e.getUsuarioId() == null) throw new IllegalArgumentException("USUARIO_ID es obligatorio");
        if (e.getTipoEntrada() == null || e.getTipoEntrada().isBlank())
            throw new IllegalArgumentException("TIPO_ENTRADA es obligatorio");
        if (e.getNombreComprador() == null || e.getNombreComprador().isBlank())
            throw new IllegalArgumentException("NOMBRE_COMPRADOR es obligatorio");
        if (e.getDniComprador() == null || e.getDniComprador().isBlank())
            throw new IllegalArgumentException("DNI_COMPRADOR es obligatorio");
        if (e.getEmailComprador() == null || e.getEmailComprador().isBlank())
            throw new IllegalArgumentException("EMAIL_COMPRADOR es obligatorio");
    }
}
