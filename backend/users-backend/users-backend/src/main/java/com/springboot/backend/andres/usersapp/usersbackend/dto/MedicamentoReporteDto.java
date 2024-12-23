package com.springboot.backend.andres.usersapp.usersbackend.dto;

public class MedicamentoReporteDto {
    
    private String nombreMedicamento;
    private int cantidadVendida;

    public MedicamentoReporteDto() {
    }

    public MedicamentoReporteDto(String nombreMedicamento, int cantidadVendida) {
        this.nombreMedicamento = nombreMedicamento;
        this.cantidadVendida = cantidadVendida;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    @Override
    public String toString() {
        return "MedicamentoReporteDto{" +
                "nombreMedicamento='" + nombreMedicamento + '\'' +
                ", cantidadVendida=" + cantidadVendida +
                '}';
    }
}
