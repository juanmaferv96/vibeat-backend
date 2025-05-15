package com.vibeat.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vibeat.backend.model.EventoOficial;
import com.vibeat.backend.service.EventoOficialService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos-oficiales")
public class EventoOficialController {

    @Autowired
    private EventoOficialService eventoOficialService;

    @GetMapping
    public List<EventoOficial> getAllEventos() {
        return eventoOficialService.getAllEventos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoOficial> getEventoById(@PathVariable Long id) {
        return eventoOficialService.getEventoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EventoOficial createEvento(@RequestBody EventoOficial evento) {
        return eventoOficialService.saveEvento(evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        eventoOficialService.deleteEvento(id);
        return ResponseEntity.noContent().build();
    }
}
