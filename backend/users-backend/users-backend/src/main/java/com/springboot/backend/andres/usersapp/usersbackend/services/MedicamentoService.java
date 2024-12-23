package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.backend.andres.usersapp.usersbackend.dto.MedicamentoStockDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Medicamento;

public interface MedicamentoService {
    List<Medicamento> listarTodo();
    Page<Medicamento> paginarTodo(Pageable pageable);
    Optional<Medicamento> buscarPorId(Long id);
    Medicamento guardar(Medicamento cliente);
    Optional<Medicamento> actualizar(Long id, Medicamento cliente);
    Optional<Medicamento> eliminar(Long id);
    List<MedicamentoStockDto> listarMedicamentosConStockAlmacen1();

    
}
