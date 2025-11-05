package com.franjazul.api.model;

import java.io.Serializable;
import java.util.Objects;

public class CitaServicioId implements Serializable {

    private Integer citaEnIntermedio;
    private Integer servicioEnIntermedio;

    // Constructor vacío
    public CitaServicioId() {
    }

    // Constructor con parámetros
    public CitaServicioId(Integer citaEnIntermedio, Integer servicioEnIntermedio) {
        this.citaEnIntermedio = citaEnIntermedio;
        this.servicioEnIntermedio = servicioEnIntermedio;
    }

    // Getters y Setters
    public Integer getCitaEnIntermedio() {
        return citaEnIntermedio;
    }

    public void setCitaEnIntermedio(Integer citaEnIntermedio) {
        this.citaEnIntermedio = citaEnIntermedio;
    }

    public Integer getServicioEnIntermedio() {
        return servicioEnIntermedio;
    }

    public void setServicioEnIntermedio(Integer servicioEnIntermedio) {
        this.servicioEnIntermedio = servicioEnIntermedio;
    }

    // equals y hashCode (obligatorios para claves compuestas)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CitaServicioId that = (CitaServicioId) o;
        if (citaEnIntermedio == null || servicioEnIntermedio == null) {
            return false;
        }
        if (that.citaEnIntermedio == null || that.servicioEnIntermedio == null) {
            return false;
        }
        return citaEnIntermedio.equals(that.citaEnIntermedio) &&
                servicioEnIntermedio.equals(that.servicioEnIntermedio);
    }

    @Override
    public int hashCode() {
        if (citaEnIntermedio == null || servicioEnIntermedio == null) {
            return 0;
        }
        return Objects.hash(citaEnIntermedio, servicioEnIntermedio);
    }
}
