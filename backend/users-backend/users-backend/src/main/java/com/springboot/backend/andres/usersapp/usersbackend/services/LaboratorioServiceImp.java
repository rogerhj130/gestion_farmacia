package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Laboratorio;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.LaboratorioRepository;

@Service
public class LaboratorioServiceImp implements LaboratorioService{
    
    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Laboratorio> listarTodo() {
        return (List<Laboratorio>) laboratorioRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Laboratorio> paginarTodo(Pageable pageable) {
        return this.laboratorioRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Laboratorio> buscarPorId(Long id) {
        return laboratorioRepository.findById(id);
    }

    @Transactional
    @Override
    public Laboratorio guardar(Laboratorio laboratorio) {
        return laboratorioRepository.save(laboratorio);
    }

    
    @Transactional
    @Override
    public Optional<Laboratorio> actualizar(Long id, Laboratorio laboratorio) {
        Optional<Laboratorio> lOptional = laboratorioRepository.findById(id);
        if (lOptional.isPresent()) {
            Laboratorio laboratorioDB = lOptional.orElseThrow();
    
            // Actualizar todos los campos
            laboratorioDB.setNombre(laboratorio.getNombre());
            laboratorioDB.setTelefono(laboratorio.getTelefono());
            laboratorioDB.setEmail(laboratorio.getEmail());
            laboratorioDB.setDireccion(laboratorio.getDireccion());
           
    
            // Guardar los cambios en la base de datos
            return Optional.of(laboratorioRepository.save(laboratorioDB));
        }
        return lOptional;
    }

    @Transactional
    @Override
    public Optional<Laboratorio> eliminar(Long id) {
        Optional<Laboratorio> lOptional = laboratorioRepository.findById(id);
        lOptional.ifPresent(cliDB -> {
            laboratorioRepository.delete(cliDB);
        });
        return lOptional;
    }
}
