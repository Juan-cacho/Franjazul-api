package com.franjazul.api.model;

import java.io.Serializable;
import java.util.Objects;

public class PermisoId implements Serializable {

    private Integer idPerEnPerm;
    private Integer idFormEnPerm;

    // Constructor vacío
    public PermisoId() {
    }

    // Constructor con parámetros
    public PermisoId(Integer idPerEnPerm, Integer idFormEnPerm) {
        this.idPerEnPerm = idPerEnPerm;
        this.idFormEnPerm = idFormEnPerm;
    }

    // Getters y Setters
    public Integer getIdPerEnPerm() {
        return idPerEnPerm;
    }

    public void setIdPerEnPerm(Integer idPerEnPerm) {
        this.idPerEnPerm = idPerEnPerm;
    }

    public Integer getIdFormEnPerm() {
        return idFormEnPerm;
    }

    public void setIdFormEnPerm(Integer idFormEnPerm) {
        this.idFormEnPerm = idFormEnPerm;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }

        PermisoId that = (PermisoId) o;

        if (!Objects.equals(idPerEnPerm, that.idPerEnPerm)) {
            return false;
        }
        if (!Objects.equals(idFormEnPerm, that.idFormEnPerm)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        if (idPerEnPerm != null) {
            result = idPerEnPerm.hashCode();
        }
        if (idFormEnPerm != null) {
            result = 31 * result + idFormEnPerm.hashCode();
        }
        return result;
    }
}