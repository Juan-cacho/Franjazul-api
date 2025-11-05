package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "MOLECULAS")
public class Moleculas {

    @Id
    @Column(name = "NOMBRE_MOL", length = 20)
    private String nombreMol;

    @Column(name = "DESCRIPCION_MOL", nullable = false, length = 100)
    private String descripcionMol;

    // Constructor vacío
    public Moleculas() {
    }

    // Constructor con parámetros
    public Moleculas(String nombreMol, String descripcionMol) {
        this.nombreMol = nombreMol;
        this.descripcionMol = descripcionMol;
    }

    // Getters y Setters
    public String getNombreMol() {
        return nombreMol;
    }

    public void setNombreMol(String nombreMol) {
        this.nombreMol = nombreMol;
    }

    public String getDescripcionMol() {
        return descripcionMol;
    }

    public void setDescripcionMol(String descripcionMol) {
        this.descripcionMol = descripcionMol;
    }
}
