package com.vibeat.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibeat.backend.model.Usuario;
import com.vibeat.backend.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Crea un nuevo usuario reforzando las reglas:
     * - DNI se guarda en MAYÚSCULAS.
     * - Email se guarda en minúsculas.
     * - Unicidad case-insensitive para dni, email y user.
     */
    public Usuario createNewUsuario(Usuario usuario) {
        if (usuario.getDni() != null) {
            usuario.setDni(usuario.getDni().toUpperCase());
        }
        if (usuario.getEmail() != null) {
            usuario.setEmail(usuario.getEmail().toLowerCase());
        }

        // Unicidades case-insensitive
        if (usuario.getEmail() != null && usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }
        if (usuario.getDni() != null && usuarioRepository.existsByDniIgnoreCase(usuario.getDni())) {
            throw new IllegalArgumentException("El DNI ya está en uso.");
        }
        if (usuario.getUser() != null && usuarioRepository.existsByUserIgnoreCase(usuario.getUser())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }

        return usuarioRepository.save(usuario);
    }

    /**
     * Autenticación por user (case-insensitive) + password.
     * Errores requeridos:
     *  - "El nombre de usuario no existe"
     *  - "Contraseña incorrecta"
     */
    public Usuario authenticate(String user, String password) {
        if (user == null) user = "";
        if (password == null) password = "";

        Optional<Usuario> opt = usuarioRepository.findByUserIgnoreCase(user);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no existe");
        }

        Usuario u = opt.get();

        // (Si hubiera hashing, aquí se verificaría con el encoder)
        if (!password.equals(u.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        return u;
    }
}
