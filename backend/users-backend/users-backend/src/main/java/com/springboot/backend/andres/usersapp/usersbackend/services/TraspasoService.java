package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Traspaso;

public interface TraspasoService{
    List<Traspaso> listarTodo();
    Page<Traspaso> paginarTodo(Pageable pageable);
    Optional<Traspaso> buscarPorId(Long id);
    Traspaso guardar(Traspaso traspaso);
    Optional<Traspaso> actualizar(Long id, Traspaso traspaso);
    Optional<Traspaso> eliminar(Long id);

}
