package com.springboot.backend.andres.usersapp.usersbackend.dto;

import java.util.List;

public class VentasReporteDto {
    private String usuarioNombre;
    private Double totalVendido;  // Total vendido en el día para ese usuario
    private List<VentaConTotalDto> ventas;  // Detalles de cada venta
    
    public VentasReporteDto(String usuarioNombre, Double totalVendido, List<VentaConTotalDto> ventas) {
        this.usuarioNombre = usuarioNombre;
        this.totalVendido = totalVendido;
        this.ventas = ventas;
    }

    public Double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(Double totalVendido) {
        this.totalVendido = totalVendido;
    }

    public List<VentaConTotalDto> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaConTotalDto> ventas) {
        this.ventas = ventas;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
}   
