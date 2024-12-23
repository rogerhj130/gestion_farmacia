package com.springboot.backend.andres.usersapp.usersbackend.dto;

import java.util.List;

public class CrearVentaDto {

    private List<CrearDetalleVentaDto> detalleVentas;
    private Long clienteId;
    private Long usuarioId;

    
    public CrearVentaDto() {
    }
    public List<CrearDetalleVentaDto> getDetalleVentas() {
        return detalleVentas;
    }
    public void setDetalleVentas(List<CrearDetalleVentaDto> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }
    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    
}
