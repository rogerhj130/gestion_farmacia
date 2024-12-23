package com.springboot.backend.andres.usersapp.usersbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CompraDto {

    private Long id;
    private Double montoTotal;
    private Integer cantidadTotal;
    private LocalDateTime fechaCompra;
    private String proveedorNombre;
    private String usuarioNombre;
    private List<DetalleCompraDto> detalleCompras;
    
    
    public CompraDto() {
    }
    
    

    public CompraDto(Long id, Double montoTotal, Integer cantidadTotal, LocalDateTime fechaCompra,
            String proveedorNombre, String usuarioNombre, List<DetalleCompraDto> detalleCompras) {
        this.id = id;
        this.montoTotal = montoTotal;
        this.cantidadTotal = cantidadTotal;
        this.fechaCompra = fechaCompra;
        this.proveedorNombre = proveedorNombre;
        this.usuarioNombre = usuarioNombre;
        this.detalleCompras = detalleCompras;
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
    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }
    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    public String getProveedorNombre() {
        return proveedorNombre;
    }
    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }
    public String getUsuarioNombre() {
        return usuarioNombre;
    }
    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public List<DetalleCompraDto> getDetalleCompras() {
        return detalleCompras;
    }

    public void setDetalleCompras(List<DetalleCompraDto> detalleCompras) {
        this.detalleCompras = detalleCompras;
    }
    

    
}
