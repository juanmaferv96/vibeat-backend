package com.vibeat.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vibeat.backend.model.Usuario;
import com.vibeat.backend.service.UsuarioService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Registro de usuario (reforzado para may/min de email/dni y unicidades case-insensitive)
    @PostMapping("/nuevo")
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario creado = usuarioService.createNewUsuario(usuario);
            return ResponseEntity.ok(creado.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // LOGIN: user case-insensitive, errores solicitados
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String user = payload.getOrDefault("user", "");
        String password = payload.getOrDefault("password", "");

        try {
            Usuario u = usuarioService.authenticate(user, password);
            // Puedes devolver datos mínimos del usuario
            return ResponseEntity.ok(Map.of(
                "id", u.getId(),
                "user", u.getUser(),
                "email", u.getEmail()
            ));
        } catch (IllegalArgumentException e) {
            // Mensajes exactos solicitados
            String msg = e.getMessage();
            return ResponseEntity.badRequest().body(Map.of("message", msg));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
