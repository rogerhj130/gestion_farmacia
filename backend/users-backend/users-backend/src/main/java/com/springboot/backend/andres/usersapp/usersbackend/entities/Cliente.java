package com.springboot.backend.andres.usersapp.usersbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ci;
    private String nombre;
    private String paterno;
    
    public Cliente() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCi() {
        return ci;
    }
    public void setCi(Long ci) {
        this.ci = ci;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPaterno() {
        return paterno;
    }
    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }
}
