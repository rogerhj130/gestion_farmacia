package com.springboot.backend.andres.usersapp.usersbackend.dto;

public class DetalleVentaDto {

    private String medicamentoNombre;
    private Double medicamentoPrecio;
    private Integer cantidadTipo;
    private Double montoTipo;

    public DetalleVentaDto() {}

    public DetalleVentaDto(String medicamentoNombre, Double medicamentoPrecio, Integer cantidadTipo, Double montoTipo) {
        this.medicamentoNombre = medicamentoNombre;
        this.medicamentoPrecio = medicamentoPrecio;
        this.cantidadTipo = cantidadTipo;
        this.montoTipo = montoTipo;
    }

    public String getMedicamentoNombre() {
        return medicamentoNombre;
    }

    public void setMedicamentoNombre(String medicamentoNombre) {
        this.medicamentoNombre = medicamentoNombre;
    }

    public Double getPrecioMedicamento() {
        return medicamentoPrecio;
    }

    public void setPrecioMedicamento(Double medicamentoPrecio) {
        this.medicamentoPrecio = medicamentoPrecio;
    }
    public Integer getCantidadTipo() {
        return cantidadTipo;
    }

    public void setCantidadTipo(Integer cantidadTipo) {
        this.cantidadTipo = cantidadTipo;
    }

    public Double getMontoTipo() {
        return montoTipo;
    }

    public void setMontoTipo(Double montoTipo) {
        this.montoTipo = montoTipo;
    }

    
}
