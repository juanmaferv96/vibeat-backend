package com.vibeat.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibeat.backend.model.EventoNoOficial;
import com.vibeat.backend.repository.EventoNoOficialRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventoNoOficialService {

    @Autowired
    private EventoNoOficialRepository eventoNoOficialRepository;

    public List<EventoNoOficial> getAllEventos() {
        return eventoNoOficialRepository.findAll();
    }

    public Optional<EventoNoOficial> getEventoById(Long id) {
        return eventoNoOficialRepository.findById(id);
    }

    public EventoNoOficial saveEvento(EventoNoOficial evento) {
        return eventoNoOficialRepository.save(evento);
    }

    public void deleteEvento(Long id) {
        eventoNoOficialRepository.deleteById(id);
    }
}
