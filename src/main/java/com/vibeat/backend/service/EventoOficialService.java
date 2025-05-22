package com.vibeat.backend.service;

import com.vibeat.backend.model.Empresa;
import com.vibeat.backend.model.EventoOficial;
import com.vibeat.backend.dto.EventoOficialDTO;
import com.vibeat.backend.repository.EmpresaRepository;
import com.vibeat.backend.repository.EventoOficialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoOficialService {

    @Autowired
    private EventoOficialRepository eventoOficialRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<EventoOficial> getEventosOficiales() {
        return eventoOficialRepository.findAll();
    }

    public EventoOficial getEventoOficial(Long id) {
        return eventoOficialRepository.findById(id).orElse(null);
    }

    public EventoOficial saveEventoOficial(EventoOficial eventoOficial) {
        return eventoOficialRepository.save(eventoOficial);
    }

    public void deleteEventoOficial(Long id) {
        eventoOficialRepository.deleteById(id);
    }

    public EventoOficialDTO getEventoOficialDTO(Long id) {
        EventoOficial evento = eventoOficialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        Empresa empresa = empresaRepository.findById(evento.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        return new EventoOficialDTO(evento, empresa);
    }
}
