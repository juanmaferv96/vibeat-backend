package com.vibeat.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibeat.backend.model.EntradaNoOficial;
import com.vibeat.backend.repository.EntradaNoOficialRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaNoOficialService {

    @Autowired
    private EntradaNoOficialRepository entradaNoOficialRepository;

    public List<EntradaNoOficial> getAllEntradas() {
        return entradaNoOficialRepository.findAll();
    }

    public Optional<EntradaNoOficial> getEntradaById(Long id) {
        return entradaNoOficialRepository.findById(id);
    }

    public EntradaNoOficial saveEntrada(EntradaNoOficial entrada) {
        return entradaNoOficialRepository.save(entrada);
    }

    public void deleteEntrada(Long id) {
        entradaNoOficialRepository.deleteById(id);
    }
}
