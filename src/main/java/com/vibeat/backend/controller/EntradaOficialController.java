package com.vibeat.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vibeat.backend.model.EntradaOficial;
import com.vibeat.backend.service.EntradaOficialService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entradas-oficiales")
public class EntradaOficialController {

    @Autowired
    private EntradaOficialService entradaOficialService;

    @GetMapping
    public List<EntradaOficial> getAllEntradas() {
        return entradaOficialService.getAllEntradas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaOficial> getEntradaById(@PathVariable Long id) {
        return entradaOficialService.getEntradaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EntradaOficial createEntrada(@RequestBody EntradaOficial entrada) {
        return entradaOficialService.saveEntrada(entrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) {
        entradaOficialService.deleteEntrada(id);
        return ResponseEntity.noContent().build();
    }
}
