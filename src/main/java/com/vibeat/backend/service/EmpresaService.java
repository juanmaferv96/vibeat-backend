package com.vibeat.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibeat.backend.model.Empresa;
import com.vibeat.backend.repository.EmpresaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> getEmpresaById(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresa saveEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void deleteEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }
    
    public Empresa createNewEmpresa(Empresa empresa) {
        if (empresaRepository.existsByEmail(empresa.getEmail()) ||
            empresaRepository.existsByCif(empresa.getCif()) ||
            empresaRepository.existsByUser(empresa.getUser())) {
            throw new IllegalArgumentException("La empresa ya existe con estos datos.");
        }
        return empresaRepository.save(empresa);
    }
}
