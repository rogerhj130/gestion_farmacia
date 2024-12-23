package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Laboratorio;

public interface LaboratorioService {
    List<Laboratorio> listarTodo();
    Page<Laboratorio> paginarTodo(Pageable pageable);
    Optional<Laboratorio> buscarPorId(Long id);
    Laboratorio guardar(Laboratorio laboratorio);
    Optional<Laboratorio> actualizar(Long id, Laboratorio la);
    Optional<Laboratorio> eliminar(Long id);
}
