package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.backend.andres.usersapp.usersbackend.dto.CrearVentaDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.VentaDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.VentasReporteDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Venta;

public interface VentaService {
    List<Venta> listarTodo();
    Page<Venta> paginarTodo(Pageable pageable);
    Optional<Venta> buscarPorId(Long id);
    Venta guardar(Venta venta);
    //Venta crearVentaConDetalle(Venta venta); //usado
    Optional<Venta> eliminar(Long id);

    VentaDto convertirAVentaDTO(Venta venta);
    List<String> crearVentaConDetalle(CrearVentaDto crearVentaDTO);
    VentasReporteDto getVentasPorUsuarioYFecha(Long usuarioId, LocalDate fecha);
    VentasReporteDto getVentasPorUsuarioYRangoFechas(Long usuarioId, LocalDate fechaInicio, LocalDate fechaFin);
}
