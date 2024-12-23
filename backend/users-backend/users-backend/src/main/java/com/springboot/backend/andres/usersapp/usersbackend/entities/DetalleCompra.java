package com.springboot.backend.andres.usersapp.usersbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_compras")
public class DetalleCompra {


    @EmbeddedId
    private DetalleCompraId id;

    private Double montoTipo;
    private Integer cantidadTipo;

    @ManyToOne
    @JsonIgnoreProperties({"detalleCompras", "handler", "hibernateLazyInitializer"})
    @MapsId("medicamentoId")
    private Medicamento medicamento;

    @ManyToOne
    @JsonIgnoreProperties({"detalleCompras", "handler", "hibernateLazyInitializer"})
    @MapsId("compraId")
    private Compra compra;

    
    public DetalleCompra() {
    }

    public DetalleCompraId getId() {
        return id;
    }

    public void setId(DetalleCompraId id) {
        this.id = id;
    }

    public Double getMontoTipo() {
        return montoTipo;
    }

    public void setMontoTipo(Double montoTipo) {
        this.montoTipo = montoTipo;
    }

    public Integer getCantidadTipo() {
        return cantidadTipo;
    }

    public void setCantidadTipo(Integer cantidadTipo) {
        this.cantidadTipo = cantidadTipo;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    


}
