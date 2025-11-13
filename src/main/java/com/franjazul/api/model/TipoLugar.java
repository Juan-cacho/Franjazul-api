package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TIPO_LUGAR")
public class TipoLugar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_lugar")
    @SequenceGenerator(name = "seq_tipo_lugar", sequenceName = "SEQ_TIPO_LUGAR", allocationSize = 1)
    @Column(name = "ID_TL")
    private Integer idTl;

    @Column(name = "NOMBRE_TL", nullable = false, length = 50)
    private String nombreTl;

    // Constructor vacío
    public TipoLugar() {
    }

    // Constructor SIN ID (para crear)
    public TipoLugar(String nombreTl) {
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
