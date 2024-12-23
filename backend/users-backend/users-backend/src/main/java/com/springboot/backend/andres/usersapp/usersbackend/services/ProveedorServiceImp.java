package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.springboot.backend.andres.usersapp.usersbackend.entities.Proveedor;

import com.springboot.backend.andres.usersapp.usersbackend.repositories.ProveedorRepository;


@Service
public class ProveedorServiceImp implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Proveedor> listarTodo() {
        return (List<Proveedor>) proveedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Proveedor> paginarTodo(Pageable pageable) {
        return this.proveedorRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    @Transactional
    @Override
    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    
    @Transactional
    @Override
    public Optional<Proveedor> actualizar(Long id, Proveedor proveedor) {
        Optional<Proveedor> pOptional = proveedorRepository.findById(id);
        if (pOptional.isPresent()) {
            Proveedor proveedorDB = pOptional.orElseThrow();
    
            // Actualizar todos los campos
            proveedorDB.setNombre(proveedor.getNombre());
            proveedorDB.setTelefono(proveedor.getTelefono());
            proveedorDB.setEmail(proveedor.getEmail());
            proveedorDB.setDireccion(proveedor.getDireccion());
           
    
            // Guardar los cambios en la base de datos
            return Optional.of(proveedorRepository.save(proveedorDB));
        }
        return pOptional;
    }

    @Transactional
    @Override
    public Optional<Proveedor> eliminar(Long id) {
        Optional<Proveedor> pOptional = proveedorRepository.findById(id);
        pOptional.ifPresent(proDB -> {
            proveedorRepository.delete(proDB);
        });
        return pOptional;
    }
}
