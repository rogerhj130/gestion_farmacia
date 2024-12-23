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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.backend.andres.usersapp.usersbackend.entities.Proveedor;

import com.springboot.backend.andres.usersapp.usersbackend.services.ProveedorService;

import com.springboot.backend.andres.usersapp.usersbackend.validation.ProveedorValidation;

import jakarta.validation.Valid;


@CrossOrigin(origins={"http://localhost:4500"})
@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorValidation validation;

    @GetMapping
    public List<Proveedor> listar() {
        return proveedorService.listarTodo();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Proveedor> proveedor = proveedorService.paginarTodo(pageable);
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Proveedor> pOptional = proveedorService.buscarPorId(id);
        if (pOptional.isPresent()) {
            return ResponseEntity.ok(pOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Proveedor proveedor, BindingResult result) {
        validation.validate(proveedor, result);

        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.guardar(proveedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Proveedor proveedor,
            BindingResult result) {
    
        validation.validate(proveedor, result);

        if (result.hasErrors()) {
            return validation(result); 
        }

        Optional<Proveedor> clienteOptional = proveedorService.actualizar(id, proveedor);

        if (clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(clienteOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Proveedor> pOptional = proveedorService.eliminar(id);
        if (pOptional.isPresent()) {
            return ResponseEntity.ok(pOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
