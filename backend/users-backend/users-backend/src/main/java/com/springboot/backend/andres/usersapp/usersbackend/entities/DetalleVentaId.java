package com.springboot.backend.andres.usersapp.usersbackend.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class DetalleVentaId implements Serializable{
    
    private Long ventaId;
    private Long medicamentoId;

    public DetalleVentaId() {
    }

    public DetalleVentaId(Long ventaId, Long medicamentoId) {
        this.ventaId = ventaId;
        this.medicamentoId = medicamentoId;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public Long getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ventaId == null) ? 0 : ventaId.hashCode());
        result = prime * result + ((medicamentoId == null) ? 0 : medicamentoId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DetalleVentaId other = (DetalleVentaId) obj;
        if (ventaId == null) {
            if (other.ventaId != null)
                return false;
        } else if (!ventaId.equals(other.ventaId))
            return false;
        if (medicamentoId == null) {
            if (other.medicamentoId != null)
                return false;
        } else if (!medicamentoId.equals(other.medicamentoId))
            return false;
        return true;
    }
}   
