package com.franjazul.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PERFILES")
public class Perfiles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_perfiles")
    @SequenceGenerator(name = "seq_perfiles", sequenceName = "SEQ_PERFILES", allocationSize = 1)
    @Column(name = "ID_PER")
    private Integer idPer;

    @Column(name = "NOMBRE_PER", nullable = false, length = 20)
    private String nombrePer;

    @Column(name = "DESCRIPCION_PER", nullable = false, length = 100)
    private String descripcionPer;

    @ManyToOne
    @JoinColumn(name = "ID_ROL_EN_PER", nullable = false)
    private Rolles rol;

    // Constructor vacío
    public Perfiles() {
    }

    public Perfiles(String nombrePer, String descripcionPer, Rolles rol) {
        this.nombrePer = nombrePer;
        this.descripcionPer = descripcionPer;
        this.rol = rol;
    }

    // Constructor con parámetros
    public Perfiles(Integer idPer, String nombrePer, String descripcionPer, Rolles rol) {
        this.idPer = idPer;
        this.nombrePer = nombrePer;
        this.descripcionPer = descripcionPer;
        this.rol = rol;
    }

    // Getters y Setters
    public Integer getIdPer() {
        return idPer;
    }

    public void setIdPer(Integer idPer) {
        this.idPer = idPer;
    }

    public String getNombrePer() {
        return nombrePer;
    }

    public void setNombrePer(String nombrePer) {
        this.nombrePer = nombrePer;
    }

    public String getDescripcionPer() {
        return descripcionPer;
    }

    public void setDescripcionPer(String descripcionPer) {
        this.descripcionPer = descripcionPer;
    }

    public Rolles getRol() {
        return rol;
    }

    public void setRol(Rolles rol) {
        this.rol = rol;
    }

}