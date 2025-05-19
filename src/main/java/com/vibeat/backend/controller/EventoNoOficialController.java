package com.vibeat.backend.controller;

import com.vibeat.backend.model.EventoNoOficial;
import com.vibeat.backend.service.EventoNoOficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos-no-oficiales")
@CrossOrigin(origins = "http://localhost:3000")
public class EventoNoOficialController {

    @Autowired
    private EventoNoOficialService eventoNoOficialService;

    @PostMapping("/nuevo")
    public ResponseEntity<EventoNoOficial> crearEvento(@RequestBody EventoNoOficial evento) {
        EventoNoOficial guardado = eventoNoOficialService.saveEvento(evento);
        return ResponseEntity.ok(guardado);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        eventoNoOficialService.deleteEvento(id);
        return ResponseEntity.noContent().build();
    }
}
