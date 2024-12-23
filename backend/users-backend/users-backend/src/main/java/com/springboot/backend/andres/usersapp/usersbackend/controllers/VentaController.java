package com.springboot.backend.andres.usersapp.usersbackend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.andres.usersapp.usersbackend.dto.CrearVentaDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.VentaDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.VentaReporteRangoRequestDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.VentaReporteRequestDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.VentasReporteDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Venta;
import com.springboot.backend.andres.usersapp.usersbackend.services.VentaService;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

  

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDto>> listar() {
        List<VentaDto> ventasDTO = ventaService.listarTodo()
                .stream()
                .map(ventaService::convertirAVentaDTO) // Llamada al método que convierte la entidad a DTO
                .toList();
        return ResponseEntity.ok(ventasDTO);
    }

    // Paginación esta en formato DTO
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<VentaDto>> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<VentaDto> ventasDTO = ventaService.paginarTodo(pageable)
                .map(ventaService::convertirAVentaDTO); // Llamada al método que convierte la entidad a DTO
        return ResponseEntity.ok(ventasDTO);
    }

    // Ver detalles de una venta específica en formato DTO
    @GetMapping("/{id}")
    public ResponseEntity<VentaDto> ver(@PathVariable Long id) {
        Optional<Venta> ventaOptional = ventaService.buscarPorId(id);
        if (ventaOptional.isPresent()) {
            VentaDto ventaDTO = ventaService.convertirAVentaDTO(ventaOptional.get());
            return ResponseEntity.ok(ventaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<List<String>> venderMedicamentos(@RequestBody CrearVentaDto crearVentaDTO) {
    List<String> mensajes = ventaService.crearVentaConDetalle(crearVentaDTO);
    return ResponseEntity.ok(mensajes);
}

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Venta> vOptional = ventaService.eliminar(id);
        if (vOptional.isPresent()) {
            return ResponseEntity.ok(vOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/usuario/ventas")
    public ResponseEntity<?> getVentasPorUsuarioYFecha(@RequestBody VentaReporteRequestDto requestDto) {
        try {
            // Llamar al servicio para obtener las ventas y el total vendido
            VentasReporteDto ventasReporte = ventaService.getVentasPorUsuarioYFecha(requestDto.getUsuarioId(),
                    requestDto.getFecha());

            return ResponseEntity.ok(ventasReporte);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al obtener los datos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/usuario/ventas/rango")
    public ResponseEntity<?> getVentasPorUsuarioYRangoFechas(@RequestBody VentaReporteRangoRequestDto requestDto) {
        try {
            // Llamar al servicio para obtener las ventas y el total vendido
            VentasReporteDto ventasReporte = ventaService.getVentasPorUsuarioYRangoFechas(
                    requestDto.getUsuarioId(),
                    requestDto.getFechaInicio(),
                    requestDto.getFechaFin());

            return ResponseEntity.ok(ventasReporte);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al obtener los datos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
