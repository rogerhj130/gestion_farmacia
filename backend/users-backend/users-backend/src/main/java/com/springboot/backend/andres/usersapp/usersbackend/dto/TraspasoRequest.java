package com.springboot.backend.andres.usersapp.usersbackend.dto;

public class TraspasoRequest {
    private Long medicamentoId;
    private Integer cantidad;

    public Long getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
