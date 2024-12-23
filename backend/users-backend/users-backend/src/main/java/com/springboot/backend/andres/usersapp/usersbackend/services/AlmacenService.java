package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.backend.andres.usersapp.usersbackend.dto.AlmacenMedicamentosDTO;
import com.springboot.backend.andres.usersapp.usersbackend.dto.TraspasoRequest;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Almacen;

public interface AlmacenService {
    List<Almacen> listarTodo();
    Page<Almacen> paginarTodo(Pageable pageable);
    Optional<Almacen> buscarPorId(Long id);
    Almacen guardar(Almacen almacen);
    Optional<Almacen> actualizar(Long id, Almacen almacen);
    Optional<Almacen> eliminar(Long id);
    
    List<String> traspasarMedicamentos(List<TraspasoRequest> traspasos);
    boolean existeElAlmacen(String almacenNombre);
    AlmacenMedicamentosDTO listarMedicamentosPorAlmacen(Long almacenId);


}
