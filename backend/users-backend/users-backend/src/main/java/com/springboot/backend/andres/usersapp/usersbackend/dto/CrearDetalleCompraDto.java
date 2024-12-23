package com.springboot.backend.andres.usersapp.usersbackend.dto;

public class CrearDetalleCompraDto {
    
    private Long medicamentoId;
    private Integer cantidadTipo;

    
    public CrearDetalleCompraDto(Long medicamentoId, Integer cantidadTipo) {
        this.medicamentoId = medicamentoId;
        this.cantidadTipo = cantidadTipo;
    }
    public CrearDetalleCompraDto() {
    }
    public Long getMedicamentoId() {
        return medicamentoId;
    }
    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }
    public Integer getCantidadTipo() {
        return cantidadTipo;
    }
    public void setCantidadTipo(Integer cantidadTipo) {
        this.cantidadTipo = cantidadTipo;
    }
    
}
