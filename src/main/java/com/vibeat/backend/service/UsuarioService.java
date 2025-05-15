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

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public Usuario createNewUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }
        if (usuarioRepository.existsByDni(usuario.getDni())) {
            throw new IllegalArgumentException("El DNI ya está en uso.");
        }
        if (usuarioRepository.existsByUser(usuario.getUser())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }
        return usuarioRepository.save(usuario);
    }
}
