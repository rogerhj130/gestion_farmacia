package com.springboot.backend.andres.usersapp.usersbackend.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_almacenes")
public class DetalleAlmacen {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @EmbeddedId
    private DetalleAlmacenId id;

    private Integer stock;
    private String estado;

    @ManyToOne
    @JsonIgnoreProperties({"detalleAlmacenes", "handler", "hibernateLazyInitializer"})
    @MapsId("medicamentoId")
    private Medicamento medicamento;

    @ManyToOne
    @JsonIgnoreProperties({"detalleAlmacenes", "handler", "hibernateLazyInitializer"})
    @MapsId("almacenId")
    private Almacen almacen;

    @OneToMany
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnoreProperties({"detalleAlmacenes", "handler", "hibernateLazyInitializer"})
    private List<DetalleTraspaso> detalleTraspasos;    
   
    
    public DetalleAlmacen() {
        detalleTraspasos = new ArrayList<>();
    }
    public DetalleAlmacen(DetalleAlmacenId id, Integer stock, String estado, Medicamento medicamento, Almacen almacen,
            List<DetalleTraspaso> detalleTraspasos) {
        this.id = id;
        this.stock = stock;
        this.estado = estado;
        this.medicamento = medicamento;
        this.almacen = almacen;
        this.detalleTraspasos = detalleTraspasos;
    }

  
    public DetalleAlmacen(DetalleAlmacenId id, Integer stock) {
        this.id = id;
        this.stock = stock;
    }

    public DetalleAlmacenId getId() {
        return id;
    }

    public void setId(DetalleAlmacenId id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public List<DetalleTraspaso> getDetalleTraspasos() {
        return detalleTraspasos;
    }

    public void setDetalleTraspasos(List<DetalleTraspaso> detalleTraspasos) {
        this.detalleTraspasos = detalleTraspasos;
    }

    
    
}
