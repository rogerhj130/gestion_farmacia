package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.backend.andres.usersapp.usersbackend.dto.CompraDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.CrearCompraDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Compra;

public interface CompraService {
    List<Compra> listarTodo();
    Page<Compra> paginarTodo(Pageable pageable);
    Optional<Compra> buscarPorId(Long id);
    Compra guardar(Compra compra);
    Compra crearCompraConDetalle(CrearCompraDto crearCompraDTO);
    //Compra crearCompraConDetalle(Compra compra);
    Optional<Compra> eliminar(Long id);

    CompraDto convertirACompraDTO(Compra compra);
    
}
