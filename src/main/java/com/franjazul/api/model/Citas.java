package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CITAS")
public class Citas {

    @Id
    @Column(name = "ID_CITA")
    private Integer idCita;

    @Column(name = "OBSERVACIONES_CITA", nullable = false, length = 300)
    private String observacionesCita;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "US_TECNICO", referencedColumnName = "ID_USUARIO", nullable = false)
    private Usuarios usuarioTecnico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "US_CREO", referencedColumnName = "ID_USUARIO", nullable = false)
    private Usuarios usuarioCreo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FRANJA_EN_CITA", referencedColumnName = "ID_FRANJA", nullable = false)
    private FranjasHorarias franjaHoraria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LUGAR_EN_CITA", referencedColumnName = "ID_LUGAR", nullable = false)
    private Lugares lugar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EC_EN_CITA", referencedColumnName = "NOMBRE_EC", nullable = false)
    private EstadoCita estadoCita;

    // Constructor vacío
    public Citas() {
    }

    // Constructor con parámetros
    public Citas(Integer idCita, String observacionesCita, Usuarios usuarioTecnico,
                 Usuarios usuarioCreo, FranjasHorarias franjaHoraria, Lugares lugar,
                 EstadoCita estadoCita) {
        this.idCita = idCita;
        this.observacionesCita = observacionesCita;
        this.usuarioTecnico = usuarioTecnico;
        this.usuarioCreo = usuarioCreo;
        this.franjaHoraria = franjaHoraria;
        this.lugar = lugar;
        this.estadoCita = estadoCita;
    }

    // Getters y Setters
    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getObservacionesCita() {
        return observacionesCita;
    }

    public void setObservacionesCita(String observacionesCita) {
        this.observacionesCita = observacionesCita;
    }

    public Usuarios getUsuarioTecnico() {
        return usuarioTecnico;
    }

    public void setUsuarioTecnico(Usuarios usuarioTecnico) {
        this.usuarioTecnico = usuarioTecnico;
    }

    public Usuarios getUsuarioCreo() {
        return usuarioCreo;
    }

    public void setUsuarioCreo(Usuarios usuarioCreo) {
        this.usuarioCreo = usuarioCreo;
    }

    public FranjasHorarias getFranjaHoraria() {
        return franjaHoraria;
    }

    public void setFranjaHoraria(FranjasHorarias franjaHoraria) {
        this.franjaHoraria = franjaHoraria;
    }

    public Lugares getLugar() {
        return lugar;
    }

    public void setLugar(Lugares lugar) {
        this.lugar = lugar;
    }

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }
}
