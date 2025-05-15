package com.vibeat.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibeat.backend.model.EventoOficial;
import com.vibeat.backend.repository.EventoOficialRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventoOficialService {

    @Autowired
    private EventoOficialRepository eventoOficialRepository;

    public List<EventoOficial> getAllEventos() {
        return eventoOficialRepository.findAll();
    }

    public Optional<EventoOficial> getEventoById(Long id) {
        return eventoOficialRepository.findById(id);
    }

    public EventoOficial saveEvento(EventoOficial evento) {
        return eventoOficialRepository.save(evento);
    }

    public void deleteEvento(Long id) {
        eventoOficialRepository.deleteById(id);
    }
}
