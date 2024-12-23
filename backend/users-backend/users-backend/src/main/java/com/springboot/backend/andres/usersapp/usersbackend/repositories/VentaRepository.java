package com.springboot.backend.andres.usersapp.usersbackend.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Venta;

public interface VentaRepository extends CrudRepository<Venta, Long>{
    Page<Venta> findAll(Pageable pageable);
    List<Venta> findByFechaVentaBetween(LocalDateTime startDate, LocalDateTime endDate);
    // Obtener ventas de un usuario en un rango de fechas
    List<Venta> findByUsuarioIdAndFechaVentaBetween(Long usuarioId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    // Nuevo método para obtener ventas de un usuario en una fecha específica
    List<Venta> findByUsuarioIdAndFechaVenta(Long usuarioId, LocalDateTime fechaVenta);
}
