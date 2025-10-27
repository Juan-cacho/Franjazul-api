package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLLES")
public class Rolles {

    @Id
    @Column(name = "ID_ROL")
    private Long idRol;

    @Column(name = "NOMBRE_ROL", nullable = false, length = 20)
    private String nombreRol;

    @Column(name = "DESCRIPCION_ROL", nullable = false, length = 100)
    private String descripcionRol;

    // Constructor vacío
    public Rolles() {
    }

    // Constructor con parámetros
    public Rolles(Long idRol, String nombreRol, String descripcionRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.descripcionRol = descripcionRol;
    }

    // Getters y Setters
    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }


}