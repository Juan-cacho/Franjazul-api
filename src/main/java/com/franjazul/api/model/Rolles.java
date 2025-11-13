package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLLES")
public class Rolles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rolles")
    @SequenceGenerator(name = "seq_rolles", sequenceName = "SEQ_ROLLES", allocationSize = 1)
    @Column(name = "ID_ROL")
    private Integer idRol;

    @Column(name = "NOMBRE_ROL", nullable = false, length = 20)
    private String nombreRol;

    @Column(name = "DESCRIPCION_ROL", nullable = false, length = 100)
    private String descripcionRol;

    // Constructor vacío
    public Rolles() {
    }

    // Constructor sin ID (para crear nuevos roles)
    public Rolles(String nombreRol, String descripcionRol) {
        this.nombreRol = nombreRol;
        this.descripcionRol = descripcionRol;
    }

    // Constructor completo
    public Rolles(Integer idRol, String nombreRol, String descripcionRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.descripcionRol = descripcionRol;
    }

    // Getters y Setters
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
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
