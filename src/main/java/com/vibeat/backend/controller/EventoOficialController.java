package com.vibeat.backend.controller;

import com.vibeat.backend.model.EventoOficial;
import com.vibeat.backend.dto.EventoOficialDTO;
import com.vibeat.backend.service.EventoOficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos-oficiales")
public class EventoOficialController {

    @Autowired
    private EventoOficialService eventoOficialService;

    @GetMapping
    public List<EventoOficial> getEventosOficiales() {
        return eventoOficialService.getEventosOficiales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoOficialDTO> getEventoOficial(@PathVariable Long id) {
        EventoOficialDTO dto = eventoOficialService.getEventoOficialDTO(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public EventoOficial crearEventoOficial(@RequestBody EventoOficial eventoOficial) {
        return eventoOficialService.saveEventoOficial(eventoOficial);
    }

    @DeleteMapping("/{id}")
    public void eliminarEventoOficial(@PathVariable Long id) {
        eventoOficialService.deleteEventoOficial(id);
    }
}
