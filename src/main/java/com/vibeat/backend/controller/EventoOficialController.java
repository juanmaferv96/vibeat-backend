package com.vibeat.backend.controller;

import com.vibeat.backend.model.EventoOficial;
import com.vibeat.backend.service.EventoOficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos-oficiales")
@CrossOrigin(origins = "http://localhost:3000")
public class EventoOficialController {

    @Autowired
    private EventoOficialService eventoOficialService;

    @PostMapping("/nuevo")
    public ResponseEntity<EventoOficial> crearEvento(@RequestBody EventoOficial evento) {
        EventoOficial guardado = eventoOficialService.saveEvento(evento);
        return ResponseEntity.ok(guardado);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        eventoOficialService.deleteEvento(id);
        return ResponseEntity.noContent().build();
    }
}
