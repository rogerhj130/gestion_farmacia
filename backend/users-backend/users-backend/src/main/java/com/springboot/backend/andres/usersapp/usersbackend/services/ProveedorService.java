package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Proveedor;

public interface ProveedorService {
    List<Proveedor> listarTodo();
    Page<Proveedor> paginarTodo(Pageable pageable);
    Optional<Proveedor> buscarPorId(Long id);
    Proveedor guardar(Proveedor proveedor);
    Optional<Proveedor> actualizar(Long id, Proveedor proveedor);
    Optional<Proveedor> eliminar(Long id);
}
