package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ESTADO_CITA")
public class EstadoCita {

    @Id
    @Column(name = "NOMBRE_EC", length = 10)
    private String nombreEc;

    @Column(name = "DESCRIPCION_EC", nullable = false, length = 30)
    private String descripcionEc;

    // Constructor vacío
    public EstadoCita() {
    }

    // Constructor con parámetros
    public EstadoCita(String nombreEc, String descripcionEc) {
        this.nombreEc = nombreEc;
        this.descripcionEc = descripcionEc;
    }

    // Getters y Setters
    public String getNombreEc() {
        return nombreEc;
    }

    public void setNombreEc(String nombreEc) {
        this.nombreEc = nombreEc;
    }

    public String getDescripcionEc() {
        return descripcionEc;
    }

    public void setDescripcionEc(String descripcionEc) {
        this.descripcionEc = descripcionEc;
    }
}
