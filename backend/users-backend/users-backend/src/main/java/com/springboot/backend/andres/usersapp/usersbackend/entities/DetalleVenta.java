package com.springboot.backend.andres.usersapp.usersbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_ventas")
public class DetalleVenta {
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @EmbeddedId
    private DetalleVentaId id;

    private Double montoTipo;
    private Integer cantidadTipo;
    
    @ManyToOne
    @JsonIgnoreProperties({"detalleVentas", "handler", "hibernateLazyInitializer"})
    @MapsId("medicamentoId")
    private Medicamento medicamento;
    
    @ManyToOne
    @JsonIgnoreProperties({"detalleVentas", "handler", "hibernateLazyInitializer"})
    @MapsId("ventaId")
    private Venta venta;

    
    public DetalleVenta() {
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

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }
    public DetalleVentaId getId() {
        return id;
    }
    public void setId(DetalleVentaId id) {
        this.id = id;
    }
}
