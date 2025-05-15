package com.vibeat.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vibeat.backend.model.EntradaNoOficial;
import com.vibeat.backend.service.EntradaNoOficialService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entradas-no-oficiales")
public class EntradaNoOficialController {

    @Autowired
    private EntradaNoOficialService entradaNoOficialService;

    @GetMapping
    public List<EntradaNoOficial> getAllEntradas() {
        return entradaNoOficialService.getAllEntradas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaNoOficial> getEntradaById(@PathVariable Long id) {
        return entradaNoOficialService.getEntradaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EntradaNoOficial createEntrada(@RequestBody EntradaNoOficial entrada) {
        return entradaNoOficialService.saveEntrada(entrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) {
        entradaNoOficialService.deleteEntrada(id);
        return ResponseEntity.noContent().build();
    }
}
