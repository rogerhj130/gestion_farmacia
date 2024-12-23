package com.springboot.backend.andres.usersapp.usersbackend.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "traspasos")
public class Traspaso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaTraspaso;

    @ManyToOne
    private Almacen almacenOrigen;

    @ManyToOne
    private Almacen almacenDestino;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "traspaso")
    @JsonIgnoreProperties({ "traspaso", "handler", "hibernateLazyInitializer" })
    private List<DetalleTraspaso> detalleTraspasos;

    public Traspaso(Long id, LocalDateTime fechaTraspaso, Almacen almacenOrigen, Almacen almacenDestino) {
        this.id = id;
        this.fechaTraspaso = fechaTraspaso;
        this.almacenOrigen = almacenOrigen;
        this.almacenDestino = almacenDestino;
    }

    public Traspaso() {
        detalleTraspasos = new ArrayList<>();
        fechaTraspaso = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaTraspaso() {
        return fechaTraspaso;
    }

    public void setFechaTraspaso(LocalDateTime fechaTraspaso) {
        this.fechaTraspaso = fechaTraspaso;
    }

    public Almacen getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(Almacen almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public Almacen getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(Almacen almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public List<DetalleTraspaso> getDetalleTraspasos() {
        return detalleTraspasos;
    }

    public void setDetalleTraspasos(List<DetalleTraspaso> detalleTraspasos) {
        this.detalleTraspasos = detalleTraspasos;
    }

}
