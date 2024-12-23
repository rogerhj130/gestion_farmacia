package com.springboot.backend.andres.usersapp.usersbackend.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ventas")
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montoTotal;
    private Integer cantidadTotal;
    private LocalDateTime fechaVenta;

    //principal, si muestra a medicamento
    @JsonIgnoreProperties({"venta", "handler", "hibernateLazyInitializer"})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "venta")
    private List<DetalleVenta> detalleVentas;
    
    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private User usuario;
    
    public Venta() {
        fechaVenta = LocalDateTime.now();
        detalleVentas = new ArrayList<>();
    }
    
    public Venta(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }
    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public List<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(List<DetalleVenta> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    
}
