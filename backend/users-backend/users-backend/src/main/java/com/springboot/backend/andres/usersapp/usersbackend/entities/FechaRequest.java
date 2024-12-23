package com.springboot.backend.andres.usersapp.usersbackend.entities;

import java.time.LocalDate;

public class FechaRequest {
    private LocalDate fecha;

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
}   
