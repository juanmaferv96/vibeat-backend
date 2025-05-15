package com.vibeat.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibeat.backend.model.EntradaOficial;
import com.vibeat.backend.repository.EntradaOficialRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaOficialService {

    @Autowired
    private EntradaOficialRepository entradaOficialRepository;

    public List<EntradaOficial> getAllEntradas() {
        return entradaOficialRepository.findAll();
    }

    public Optional<EntradaOficial> getEntradaById(Long id) {
        return entradaOficialRepository.findById(id);
    }

    public EntradaOficial saveEntrada(EntradaOficial entradaOficial) {
        return entradaOficialRepository.save(entradaOficial);
    }

    public void deleteEntrada(Long id) {
        entradaOficialRepository.deleteById(id);
    }
}
