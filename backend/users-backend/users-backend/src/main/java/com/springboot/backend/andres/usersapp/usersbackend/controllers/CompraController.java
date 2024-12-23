package com.springboot.backend.andres.usersapp.usersbackend.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.andres.usersapp.usersbackend.dto.CompraDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.CrearCompraDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Compra;
import com.springboot.backend.andres.usersapp.usersbackend.services.CompraService;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraDto>> listar() {
        List<CompraDto> comprasDTO = compraService.listarTodo()
                .stream()
                .map(compraService::convertirACompraDTO) // Llamada al método que convierte la entidad a DTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(comprasDTO);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<CompraDto>> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<CompraDto> comprasDTO = compraService.paginarTodo(pageable)
                .map(compraService::convertirACompraDTO); // Llamada al método que convierte la entidad a DTO
        return ResponseEntity.ok(comprasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDto> ver(@PathVariable Long id) {
        Optional<Compra> compraOptional = compraService.buscarPorId(id);
        if (compraOptional.isPresent()) {
            CompraDto compraDTO = compraService.convertirACompraDTO(compraOptional.get());
            return ResponseEntity.ok(compraDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearCompraDto compraDto) {
        try {
            // Crear la compra con sus detalles
            Compra compraGuardada = compraService.crearCompraConDetalle(compraDto);

            // Convertir la compra a DTO para la respuesta
            CompraDto compraDTO = compraService.convertirACompraDTO(compraGuardada);

            // Devolver la compra con formato DTO
            return ResponseEntity.status(HttpStatus.CREATED).body(compraDTO);
        } catch (RuntimeException ex) {
            // Captura de excepciones personalizadas desde el Service
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            // Captura de errores generales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error inesperado, intente nuevamente"));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Compra> cOptional = compraService.eliminar(id);
        if (cOptional.isPresent()) {
            return ResponseEntity.ok(cOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
