package com.springboot.backend.andres.usersapp.usersbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VentaDto {

    private Long id;
    private Double montoTotal;
    private Integer cantidadTotal;
    private LocalDateTime fechaVenta;
    private String clienteNombre;
    private String usuarioNombre;
    private List<DetalleVentaDto> detalles;

    public VentaDto() {}

    public VentaDto(Long id, Double montoTotal, Integer cantidadTotal, LocalDateTime fechaVenta, 
                    String clienteNombre, String usuarioNombre, List<DetalleVentaDto> detalles) {
        this.id = id;
        this.montoTotal = montoTotal;
        this.cantidadTotal = cantidadTotal;
        this.fechaVenta = fechaVenta;
        this.clienteNombre = clienteNombre;
        this.usuarioNombre = usuarioNombre;
        this.detalles = detalles;
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

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public List<DetalleVentaDto> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaDto> detalles) {
        this.detalles = detalles;
    }
}
