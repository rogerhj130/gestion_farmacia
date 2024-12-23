package com.springboot.backend.andres.usersapp.usersbackend.dto;

import java.time.LocalDate;

public class RangoFechasDto {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }    
}
