package com.vibeat.backend.controller;

import com.vibeat.backend.model.EntradaOficial;
import com.vibeat.backend.service.EntradaOficialService;
import com.vibeat.backend.service.PdfEntradaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entradas-oficiales")
public class EntradaOficialController {

    @Autowired
    private EntradaOficialService entradaOficialService;
    
    @Autowired
    private PdfEntradaService pdfEntradaService;

    @GetMapping
    public List<EntradaOficial> getAllEntradas() {
        return entradaOficialService.getAllEntradas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaOficial> getEntradaById(@PathVariable Long id) {
        return entradaOficialService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntradaOficial> createEntrada(@RequestBody EntradaOficial entrada) {
        return ResponseEntity.ok(entradaOficialService.saveEntrada(entrada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) {
        boolean deleted = entradaOficialService.deleteEntrada(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    // Extras
    @GetMapping("/by-codigoqr/{codigoQr}")
    public ResponseEntity<EntradaOficial> getByCodigoQr(@PathVariable String codigoQr) {
        return entradaOficialService.findByCodigoQr(codigoQr)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-referencia/{referencia}")
    public ResponseEntity<EntradaOficial> getByReferencia(@PathVariable String referencia) {
        return entradaOficialService.findByReferencia(referencia)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-usuario/{usuarioId}")
    public List<EntradaOficial> getByUsuario(@PathVariable Long usuarioId) {
        return entradaOficialService.findByUsuarioId(usuarioId);
    }

    @GetMapping("/by-evento/{eventoId}")
    public List<EntradaOficial> getByEvento(@PathVariable Long eventoId) {
        return entradaOficialService.findByEventoId(eventoId);
    }
    
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(
            @PathVariable Long id,
            @RequestParam(required = false) String eventoNombre,
            @RequestParam(required = false) String usuarioLogin,
            @RequestParam(name = "tipoDesc", required = false) String tipoDesc) {
        return pdfEntradaService.descargarPdfOficial(id, eventoNombre, usuarioLogin, tipoDesc);
    }


}
