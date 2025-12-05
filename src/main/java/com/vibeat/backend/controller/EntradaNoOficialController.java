package com.vibeat.backend.controller;

import com.vibeat.backend.model.EntradaNoOficial;
import com.vibeat.backend.service.EntradaNoOficialService;
import com.vibeat.backend.service.PdfEntradaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createEntrada(@RequestBody EntradaNoOficial entrada) {
        try {
            EntradaNoOficial nueva = entradaNoOficialService.saveEntrada(entrada);
            return ResponseEntity.ok(nueva);
        } catch (IllegalStateException e) {
            // Capturamos el error de stock agotado
            if ("AGOTADAS".equals(e.getMessage())) {
                return ResponseEntity.status(409).body(Map.of("message", "Lo sentimos, las entradas de este tipo se han agotado."));
            }
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Error interno al procesar la compra."));
        }
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
    public ResponseEntity<byte[]> descargarPdf(
            @PathVariable Long id,
            @RequestParam(required = false) String eventoNombre,
            @RequestParam(required = false) String eventoDesc,
            @RequestParam(required = false) String usuarioLogin,
            @RequestParam(name = "tipoDesc", required = false) String tipoDesc) {
    	return pdfEntradaService.descargarPdfNoOficial(id, eventoNombre, eventoDesc, usuarioLogin, tipoDesc);  
    }
    
    @PostMapping("/scan")
    public ResponseEntity<?> scan(@RequestBody Map<String, String> payload) {
        try {
            Long eventoId = Long.valueOf(payload.get("eventoId"));
            String codigoQr = payload.get("codigoQr");

            Map<String, Object> data = entradaNoOficialService.validarYMarcarEscaneo(eventoId, codigoQr);

            // 200 OK → válido y marcado como escaneado
            return ResponseEntity.ok(data);

        } catch (IllegalStateException ise) {
            // 409 → ya escaneada
            if ("ALREADY_SCANNED".equals(ise.getMessage())) {
                return ResponseEntity.status(409).body(Map.of(
                    "message", "Esta entrada ya ha sido escaneada"
                ));
            }
            return ResponseEntity.badRequest().body(Map.of("message", "Estado inválido"));

        } catch (RuntimeException re) {
            // 404 → no existe para ese evento + qr
            if ("NOT_FOUND".equals(re.getMessage())) {
                return ResponseEntity.status(404).body(Map.of(
                    "message", "ENTRADA NO VALIDA PARA ESTE EVENTO"
                ));
            }
            return ResponseEntity.status(500).body(Map.of("message", "Error interno"));

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("message", "Error interno"));
        }
    }
}