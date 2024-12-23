package com.springboot.backend.andres.usersapp.usersbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Traspaso;
import com.springboot.backend.andres.usersapp.usersbackend.services.TraspasoService;

@RestController
@RequestMapping("/api/traspasos")
public class TraspasoController {

    @Autowired
    private TraspasoService traspasoService;

    @GetMapping
    public List<Traspaso> listarTodo() {
        return traspasoService.listarTodo();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Traspaso>> paginarTodo(Pageable pageable) {
        Page<Traspaso> resultado = traspasoService.paginarTodo(pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Traspaso> buscarPorId(@PathVariable Long id) {
        return traspasoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Traspaso> guardar(@RequestBody Traspaso traspaso) {
        try {
            Traspaso nuevoTraspaso = traspasoService.guardar(traspaso);
            return ResponseEntity.ok(nuevoTraspaso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Traspaso> actualizar(@PathVariable Long id, @RequestBody Traspaso traspaso) {
        return traspasoService.actualizar(id, traspaso)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return traspasoService.eliminar(id)
                .map(traspaso -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }


}
