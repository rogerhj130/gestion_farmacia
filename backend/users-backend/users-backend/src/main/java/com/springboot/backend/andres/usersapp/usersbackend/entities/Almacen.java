package com.springboot.backend.andres.usersapp.usersbackend.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "almacenes")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnoreProperties({"almacen", "handler", "hibernateLazyInitializer"})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "almacen")
    private List<DetalleAlmacen> detalleAlmacenes;

    
    public Almacen() {
        detalleAlmacenes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<DetalleAlmacen> getDetalleAlmacenes() {
        return detalleAlmacenes;
    }

    public void setDetalleAlmacenes(List<DetalleAlmacen> detalleAlmacenes) {
        this.detalleAlmacenes = detalleAlmacenes;
    }

    
}
