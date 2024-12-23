package com.springboot.backend.andres.usersapp.usersbackend.dto;

import java.util.List;

public class CrearCompraDto {

    private Long proveedorId;
    private Long usuarioId;
    private List<CrearDetalleCompraDto> detalleCompras;

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<CrearDetalleCompraDto> getDetalleCompras() {
        return detalleCompras;
    }

    public void setDetalleCompras(List<CrearDetalleCompraDto> detalleCompras) {
        this.detalleCompras = detalleCompras;
    }
}
