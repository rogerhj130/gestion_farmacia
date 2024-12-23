package com.springboot.backend.andres.usersapp.usersbackend.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class DetalleAlmacenId implements Serializable{

    private Long almacenId;
    private Long medicamentoId;

    
    public DetalleAlmacenId() {
    }
    public DetalleAlmacenId(Long almacenId, Long medicamentoId) {
        this.almacenId = almacenId;
        this.medicamentoId = medicamentoId;
    }
    public Long getAlmacenId() {
        return almacenId;
    }
    public void setAlmacenId(Long almacenId) {
        this.almacenId = almacenId;
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
        result = prime * result + ((almacenId == null) ? 0 : almacenId.hashCode());
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
        DetalleAlmacenId other = (DetalleAlmacenId) obj;
        if (almacenId == null) {
            if (other.almacenId != null)
                return false;
        } else if (!almacenId.equals(other.almacenId))
            return false;
        if (medicamentoId == null) {
            if (other.medicamentoId != null)
                return false;
        } else if (!medicamentoId.equals(other.medicamentoId))
            return false;
        return true;
    }

    
}
