package com.vibeat.backend.service;

import com.vibeat.backend.model.EventoNoOficial;
import com.vibeat.backend.model.Usuario;
import com.vibeat.backend.dto.EventoNoOficialDTO;
import com.vibeat.backend.repository.EventoNoOficialRepository;
import com.vibeat.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoNoOficialService {

    @Autowired
    private EventoNoOficialRepository eventoNoOficialRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<EventoNoOficial> getEventosNoOficiales() {
        return eventoNoOficialRepository.findAll();
    }

    public EventoNoOficial getEventoNoOficial(Long id) {
        return eventoNoOficialRepository.findById(id).orElse(null);
    }

    public EventoNoOficial saveEventoNoOficial(EventoNoOficial eventoNoOficial) {
        return eventoNoOficialRepository.save(eventoNoOficial);
    }

    public void deleteEventoNoOficial(Long id) {
        eventoNoOficialRepository.deleteById(id);
    }

    public EventoNoOficialDTO getEventoNoOficialDTO(Long id) {
        EventoNoOficial evento = eventoNoOficialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        Usuario usuario = usuarioRepository.findById(evento.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new EventoNoOficialDTO(evento, usuario);
    }
}
