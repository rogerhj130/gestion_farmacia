package com.springboot.backend.andres.usersapp.usersbackend.dto;

import java.time.LocalDate;

public class VentaReporteRequestDto {
     private Long usuarioId;
    private LocalDate fecha;

    // Getters y Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
