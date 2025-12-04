package com.vibeat.backend.service;

import com.vibeat.backend.model.EntradaNoOficial;
import com.vibeat.backend.repository.EntradaNoOficialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EntradaNoOficialService {

    @Autowired
    private EntradaNoOficialRepository entradaNoOficialRepository;

    @Transactional
    public EntradaNoOficial saveEntrada(EntradaNoOficial e) {
        validarRequeridos(e);

        if (e.getPremiada() == null) e.setPremiada(false);
        if (e.getEscaneada() == null) e.setEscaneada(false);

        LocalDateTime now = (e.getFechaCompra() == null) ? EntradaUtils.now() : e.getFechaCompra();
        e.setFechaCompra(now);

        String ts = now.format(EntradaUtils.TS);

        if (e.getCodigoQr() == null || e.getCodigoQr().isBlank()) {
            e.setCodigoQr("NO_" + e.getEventoId() + "_" + e.getUsuarioId() + "_" + ts);
        }
        if (e.getReferencia() == null || e.getReferencia().isBlank()) {
            e.setReferencia("NO" + e.getEventoId() + e.getUsuarioId() + ts);
        }

        int intentos = 0;
        while (true) {
            try {
                return entradaNoOficialRepository.save(e);
            } catch (DataIntegrityViolationException ex) {
                if (++intentos > 2) throw ex;
                now = now.plusSeconds(1);
                e.setFechaCompra(now);
                ts = now.format(EntradaUtils.TS);
                e.setCodigoQr("NO_" + e.getEventoId() + "_" + e.getUsuarioId() + "_" + ts);
                e.setReferencia("NO" + e.getEventoId() + e.getUsuarioId() + ts);
            }
        }
    }

    // ---- NUEVOS: todo por service ----
    @Transactional(readOnly = true)
    public List<EntradaNoOficial> getAllEntradas() {
        return entradaNoOficialRepository.findAll();
    }

    @Transactional
    public boolean deleteEntrada(Long id) {
        if (!entradaNoOficialRepository.existsById(id)) return false;
        entradaNoOficialRepository.deleteById(id);
        return true;
    }

    // ---- Consultas ----
    @Transactional(readOnly = true)
    public Optional<EntradaNoOficial> findById(Long id) {
        return entradaNoOficialRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<EntradaNoOficial> findByCodigoQr(String codigoQr) {
        return entradaNoOficialRepository.findByCodigoQr(codigoQr);
    }

    @Transactional(readOnly = true)
    public Optional<EntradaNoOficial> findByReferencia(String referencia) {
        return entradaNoOficialRepository.findByReferencia(referencia);
    }

    @Transactional(readOnly = true)
    public List<EntradaNoOficial> findByUsuarioId(Long usuarioId) {
        return entradaNoOficialRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<EntradaNoOficial> findByEventoId(Long eventoId) {
        return entradaNoOficialRepository.findByEventoId(eventoId);
    }

    @Transactional
    public void marcarEscaneada(Long id, boolean escaneada) {
        entradaNoOficialRepository.findById(id).ifPresent(e -> {
            e.setEscaneada(escaneada);
            entradaNoOficialRepository.save(e);
        });
    }

    @Transactional
    public void marcarPremiada(Long id, boolean premiada, String nombrePremio) {
        entradaNoOficialRepository.findById(id).ifPresent(e -> {
            e.setPremiada(premiada);
            e.setNombrePremio(premiada ? nombrePremio : null);
            entradaNoOficialRepository.save(e);
        });
    }

    // ---- Helper ----
    private void validarRequeridos(EntradaNoOficial e) {
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
    
    @Transactional
    public Map<String, Object> validarYMarcarEscaneo(Long eventoId, String codigoQr) {
        if (eventoId == null || codigoQr == null || codigoQr.isBlank()) {
            throw new IllegalArgumentException("Parámetros inválidos");
        }

        EntradaNoOficial entrada = entradaNoOficialRepository
                .findByEventoIdAndCodigoQr(eventoId, codigoQr)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND"));

        // En tu entidad 'escaneada' es Boolean
        if (Boolean.TRUE.equals(entrada.getEscaneada())) {
            // ya estaba escaneada
            throw new IllegalStateException("ALREADY_SCANNED");
        }

        // marcar y persistir
        entrada.setEscaneada(true);
        entradaNoOficialRepository.save(entrada);

        Map<String, Object> res = new HashMap<>();
        res.put("nombreComprador",   entrada.getNombreComprador());
        res.put("apellidosComprador",entrada.getApellidosComprador());
        res.put("dniComprador",      entrada.getDniComprador());
        return res;
    }
}
