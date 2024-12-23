package com.springboot.backend.andres.usersapp.usersbackend.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class DetalleCompraId implements Serializable{

    private Long medicamentoId;
    private Long compraId;

    public DetalleCompraId() {
        
    }
    
    public DetalleCompraId(Long medicamentoId, Long compraId) {
        this.medicamentoId = medicamentoId;
        this.compraId = compraId;
    }

    public Long getMedicamentoId() {
        return medicamentoId;
    }
    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }
    public Long getCompraId() {
        return compraId;
    }
    public void setCompraId(Long compraId) {
        this.compraId = compraId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((medicamentoId == null) ? 0 : medicamentoId.hashCode());
        result = prime * result + ((compraId == null) ? 0 : compraId.hashCode());
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
        DetalleCompraId other = (DetalleCompraId) obj;
        if (medicamentoId == null) {
            if (other.medicamentoId != null)
                return false;
        } else if (!medicamentoId.equals(other.medicamentoId))
            return false;
        if (compraId == null) {
            if (other.compraId != null)
                return false;
        } else if (!compraId.equals(other.compraId))
            return false;
        return true;
    }

    
}
