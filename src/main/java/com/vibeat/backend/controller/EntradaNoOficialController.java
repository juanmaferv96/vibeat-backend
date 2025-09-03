package com.vibeat.backend.controller;

import com.vibeat.backend.model.EntradaNoOficial;
import com.vibeat.backend.service.EntradaNoOficialService;
import com.vibeat.backend.service.PdfEntradaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entradas-no-oficiales")
public class EntradaNoOficialController {

    @Autowired
    private EntradaNoOficialService entradaNoOficialService;
    
    @Autowired
    private PdfEntradaService pdfEntradaService;

    @GetMapping
    public List<EntradaNoOficial> getAllEntradas() {
        return entradaNoOficialService.getAllEntradas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaNoOficial> getEntradaById(@PathVariable Long id) {
        return entradaNoOficialService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntradaNoOficial> createEntrada(@RequestBody EntradaNoOficial entrada) {
        return ResponseEntity.ok(entradaNoOficialService.saveEntrada(entrada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) {
        boolean deleted = entradaNoOficialService.deleteEntrada(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    // Extras
    @GetMapping("/by-codigoqr/{codigoQr}")
    public ResponseEntity<EntradaNoOficial> getByCodigoQr(@PathVariable String codigoQr) {
        return entradaNoOficialService.findByCodigoQr(codigoQr)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-referencia/{referencia}")
    public ResponseEntity<EntradaNoOficial> getByReferencia(@PathVariable String referencia) {
        return entradaNoOficialService.findByReferencia(referencia)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-usuario/{usuarioId}")
    public List<EntradaNoOficial> getByUsuario(@PathVariable Long usuarioId) {
        return entradaNoOficialService.findByUsuarioId(usuarioId);
    }

    @GetMapping("/by-evento/{eventoId}")
    public List<EntradaNoOficial> getByEvento(@PathVariable Long eventoId) {
        return entradaNoOficialService.findByEventoId(eventoId);
    }
    
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
        return pdfEntradaService.descargarPdfNoOficial(id);
    }

}
