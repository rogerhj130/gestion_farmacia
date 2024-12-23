package com.springboot.backend.andres.usersapp.usersbackend.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class DetalleTraspasoId implements Serializable{

    private Long traspasoId;
    private DetalleAlmacenId detalleAlmacenId;

    
    public DetalleTraspasoId() {
    }
    
    public DetalleTraspasoId(Long traspasoId, DetalleAlmacenId detalleAlmacenId) {
        this.traspasoId = traspasoId;
        this.detalleAlmacenId = detalleAlmacenId;
    }

    public Long getTraspasoId() {
        return traspasoId;
    }
    public void setTraspasoId(Long traspasoId) {
        this.traspasoId = traspasoId;
    }
    public DetalleAlmacenId getDetalleAlmacenId() {
        return detalleAlmacenId;
    }
    public void setDetalleAlmacenId(DetalleAlmacenId detalleAlmacenId) {
        this.detalleAlmacenId = detalleAlmacenId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((traspasoId == null) ? 0 : traspasoId.hashCode());
        result = prime * result + ((detalleAlmacenId == null) ? 0 : detalleAlmacenId.hashCode());
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
        DetalleTraspasoId other = (DetalleTraspasoId) obj;
        if (traspasoId == null) {
            if (other.traspasoId != null)
                return false;
        } else if (!traspasoId.equals(other.traspasoId))
            return false;
        if (detalleAlmacenId == null) {
            if (other.detalleAlmacenId != null)
                return false;
        } else if (!detalleAlmacenId.equals(other.detalleAlmacenId))
            return false;
        return true;
    }

    
}
