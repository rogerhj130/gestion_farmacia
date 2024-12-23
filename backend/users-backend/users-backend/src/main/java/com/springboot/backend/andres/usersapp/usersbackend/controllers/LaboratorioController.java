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

import com.springboot.backend.andres.usersapp.usersbackend.entities.Laboratorio;
import com.springboot.backend.andres.usersapp.usersbackend.services.LaboratorioService;
import com.springboot.backend.andres.usersapp.usersbackend.validation.LaboratorioValidation;

import jakarta.validation.Valid;


@CrossOrigin(origins={"http://localhost:4500"})
@RestController
@RequestMapping("/api/laboratorios")
public class LaboratorioController {

    @Autowired
    private LaboratorioService laboratorioService;

    @Autowired
    private LaboratorioValidation validation;

    @GetMapping
    public List<Laboratorio> listar() {
        return laboratorioService.listarTodo();
    }

     @GetMapping("/page/{page}")
    public ResponseEntity<?> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Laboratorio> laboratorio = laboratorioService.paginarTodo(pageable);
        return ResponseEntity.ok(laboratorio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Laboratorio> cOptional = laboratorioService.buscarPorId(id);
        if (cOptional.isPresent()) {
            return ResponseEntity.ok(cOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Laboratorio laboratorio, BindingResult result) {
      //  validation.validate(laboratorio, result);

      //  if (result.hasErrors()) {
       //     return validation(result);
      //  }
        return ResponseEntity.status(HttpStatus.CREATED).body(laboratorioService.guardar(laboratorio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Laboratorio laboratorio,
            BindingResult result) {
    
        validation.validate(laboratorio, result);

        if (result.hasErrors()) {
            return validation(result); 
        }

        Optional<Laboratorio> clienteOptional = laboratorioService.actualizar(id, laboratorio);

        if (clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(clienteOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Laboratorio no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Laboratorio> lOptional = laboratorioService.eliminar(id);
        if (lOptional.isPresent()) {
            return ResponseEntity.ok(lOptional.orElseThrow());
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
