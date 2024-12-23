package com.springboot.backend.andres.usersapp.usersbackend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

import com.springboot.backend.andres.usersapp.usersbackend.entities.Cliente;
import com.springboot.backend.andres.usersapp.usersbackend.services.ClienteService;
import com.springboot.backend.andres.usersapp.usersbackend.validation.ClienteValidation;

import jakarta.validation.Valid;

@CrossOrigin(origins={"http://localhost:4500"})
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteValidation validation;

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodo();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listarPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Cliente> clientes = clienteService.paginarTodo(pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Cliente> cOptional = clienteService.buscarPorId(id);
        if (cOptional.isPresent()) {
            return ResponseEntity.ok(cOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Cliente cliente, BindingResult result) {
        validation.validate(cliente, result);

        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.guardar(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente,
            BindingResult result) {
    
        validation.validate(cliente, result);

        if (result.hasErrors()) {
            return validation(result); 
        }

        Optional<Cliente> clienteOptional = clienteService.actualizar(id, cliente);

        if (clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(clienteOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Cliente> cOptional = clienteService.eliminar(id);
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
