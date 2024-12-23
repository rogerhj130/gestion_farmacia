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

import com.springboot.backend.andres.usersapp.usersbackend.entities.Empleado;
import com.springboot.backend.andres.usersapp.usersbackend.services.EmpleadoService;
import com.springboot.backend.andres.usersapp.usersbackend.validation.EmpleadoValidation;

import jakarta.validation.Valid;


@CrossOrigin(origins={"http://localhost:4500"})
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private EmpleadoValidation validation;

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listarTodo();
    }

   @GetMapping("/page/{page}")
    public ResponseEntity<?> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Empleado> empleados = empleadoService.paginarTodo(pageable);
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
       Optional<Empleado> eOptional = empleadoService.buscarPorId(id);
        if (eOptional.isPresent()) {
            return ResponseEntity.ok(eOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Empleado empleado, BindingResult result) {
        validation.validate(empleado, result);

        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.guardar(empleado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Empleado empleado,
            BindingResult result) {
    
        validation.validate(empleado, result);

        if (result.hasErrors()) {
            return validation(result); 
        }

        Optional<Empleado> clienteOptional = empleadoService.actualizar(id, empleado);

        if (clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(clienteOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Empleado> cOptional = empleadoService.eliminar(id);
        if (cOptional.isPresent()) {
            return ResponseEntity.ok(cOptional.orElseThrow());
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
