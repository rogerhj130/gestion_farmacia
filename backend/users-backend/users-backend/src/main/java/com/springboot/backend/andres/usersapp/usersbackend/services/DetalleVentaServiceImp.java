package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleVenta;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleVentaId;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.DetalleVentaRepository;

@Service
public class DetalleVentaServiceImp implements DetalleVentaService{
    @Autowired
    private DetalleVentaRepository repository;

    @Override
    public Iterable<DetalleVenta> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<DetalleVenta> buscarPorId(DetalleVentaId id) {
        return repository.findById(id);
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        return repository.save(detalleVenta);
    }

    @Override
    public void eliminarPorId(DetalleVentaId id) {
        repository.deleteById(id);
    }
}
