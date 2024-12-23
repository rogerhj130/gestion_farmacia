package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.Optional;

import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleVenta;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleVentaId;

public interface DetalleVentaService {
    Iterable<DetalleVenta> listarTodos();
    Optional<DetalleVenta> buscarPorId(DetalleVentaId id);
    DetalleVenta guardar(DetalleVenta detalleVenta);
    void eliminarPorId(DetalleVentaId  id);
}
