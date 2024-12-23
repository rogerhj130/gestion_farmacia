package com.springboot.backend.andres.usersapp.usersbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_traspasos")
public class DetalleTraspaso {

    @EmbeddedId
    private DetalleTraspasoId id;

    private Integer cantidad;


    @ManyToOne
    @JsonIgnoreProperties({"detalleTraspasos", "handler", "hibernateLazyInitializer"})
    @MapsId("traspasoId")
    private Traspaso traspaso;

    @ManyToOne
    @JsonIgnoreProperties({"detalleTraspasos", "handler", "hibernateLazyInitializer"})
    @MapsId("detalleAlmacenId")
    private DetalleAlmacen detalleAlmacenes;

    
    public DetalleTraspaso() {
    }
    
    public DetalleTraspaso(Integer cantidad, Traspaso traspaso, DetalleAlmacen detalleAlmacenes) {
        this.cantidad = cantidad;
        this.traspaso = traspaso;
        this.detalleAlmacenes = detalleAlmacenes;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Traspaso getTraspaso() {
        return traspaso;
    }

    public void setTraspaso(Traspaso traspaso) {
        this.traspaso = traspaso;
    }

    public DetalleAlmacen getDetalleAlmacen() {
        return detalleAlmacenes;
    }

    public void setDetalleAlmacen(DetalleAlmacen detalleAlmacenes) {
        this.detalleAlmacenes = detalleAlmacenes;
    }

    
}
