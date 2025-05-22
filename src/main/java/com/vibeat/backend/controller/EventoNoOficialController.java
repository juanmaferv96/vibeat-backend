package com.vibeat.backend.controller;

import com.vibeat.backend.model.EventoNoOficial;
import com.vibeat.backend.dto.EventoNoOficialDTO;
import com.vibeat.backend.service.EventoNoOficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos-no-oficiales")
public class EventoNoOficialController {

    @Autowired
    private EventoNoOficialService eventoNoOficialService;

    @GetMapping
    public List<EventoNoOficial> getEventosNoOficiales() {
        return eventoNoOficialService.getEventosNoOficiales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoNoOficialDTO> getEventoNoOficial(@PathVariable Long id) {
        EventoNoOficialDTO dto = eventoNoOficialService.getEventoNoOficialDTO(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public EventoNoOficial crearEventoNoOficial(@RequestBody EventoNoOficial eventoNoOficial) {
        return eventoNoOficialService.saveEventoNoOficial(eventoNoOficial);
    }

    @DeleteMapping("/{id}")
    public void eliminarEventoNoOficial(@PathVariable Long id) {
        eventoNoOficialService.deleteEventoNoOficial(id);
    }
}
