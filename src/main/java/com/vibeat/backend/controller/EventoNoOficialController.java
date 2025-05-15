package com.vibeat.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vibeat.backend.model.EventoNoOficial;
import com.vibeat.backend.service.EventoNoOficialService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos-no-oficiales")
public class EventoNoOficialController {

    @Autowired
    private EventoNoOficialService eventoNoOficialService;

    @GetMapping
    public List<EventoNoOficial> getAllEventos() {
        return eventoNoOficialService.getAllEventos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoNoOficial> getEventoById(@PathVariable Long id) {
        return eventoNoOficialService.getEventoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EventoNoOficial createEvento(@RequestBody EventoNoOficial evento) {
        return eventoNoOficialService.saveEvento(evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        eventoNoOficialService.deleteEvento(id);
        return ResponseEntity.noContent().build();
    }
}
