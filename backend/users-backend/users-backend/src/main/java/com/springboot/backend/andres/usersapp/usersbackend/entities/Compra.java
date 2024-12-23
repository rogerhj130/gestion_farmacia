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
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montoTotal;
    private Integer cantidadTotal;
    private LocalDateTime fechaCompra;

    @ManyToOne
    private Proveedor proveedor;

    @ManyToOne
    private User usuario;
    
    @JsonIgnoreProperties({"compra", "handler", "hibernateLazyInitializer"})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "compra")
    private List<DetalleCompra> detalleCompras;
    
    public Compra() {
        fechaCompra = LocalDateTime.now();
        detalleCompras = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
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

    public List<DetalleCompra> getDetalleCompras() {
        return detalleCompras;
    }

    public void setDetalleCompras(List<DetalleCompra> detalleCompras) {
        this.detalleCompras = detalleCompras;
    }
    
}
