package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TIPO_LUGAR")
public class TipoLugar {

    @Id
    @Column(name = "ID_TL")
    private Integer idTl;

    @Column(name = "NOMBRE_TL", nullable = false, length = 50)
    private String nombreTl;

    // Constructor vacío
    public TipoLugar() {
    }

    // Constructor con parámetros
    public TipoLugar(Integer idTl, String nombreTl) {
        this.idTl = idTl;
        this.nombreTl = nombreTl;
    }

    // Getters y Setters
    public Integer getIdTl() {
        return idTl;
    }

    public void setIdTl(Integer idTl) {
        this.idTl = idTl;
    }

    public String getNombreTl() {
        return nombreTl;
    }

    public void setNombreTl(String nombreTl) {
        this.nombreTl = nombreTl;
    }
}
