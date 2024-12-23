package com.springboot.backend.andres.usersapp.usersbackend.dto;

public class VentaConTotalDto {
    private Long ventaId;
    private Double montoTotal;

    public VentaConTotalDto(Long ventaId, Double montoTotal) {
        this.ventaId = ventaId;
        this.montoTotal = montoTotal;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }
}
