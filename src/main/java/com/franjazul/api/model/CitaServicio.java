package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CITA_SERVICIO")
@IdClass(CitaServicioId.class)
public class CitaServicio {

    @Id
    @Column(name = "CITA_EN_INTERMEDIO")
    private Integer citaEnIntermedio;

    @Id
    @Column(name = "SERVICIO_EN_INTERMEDIO")
    private Integer servicioEnIntermedio;

    @Column(name = "CANTIDAD_SER", nullable = false)
    private Integer cantidadSer;

    // Relación ManyToOne con Citas (FK: CITA_EN_INTERMEDIO -> CITAS.ID_CITA)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CITA_EN_INTERMEDIO", referencedColumnName = "ID_CITA", insertable = false, updatable = false)
    private Citas cita;

    // Relación ManyToOne con Servicios (FK: SERVICIO_EN_INTERMEDIO -> SERVICIOS.ID_SERVICIO)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERVICIO_EN_INTERMEDIO", referencedColumnName = "ID_SERVICIO", insertable = false, updatable = false)
    private Servicios servicio;

    // Constructor vacío
    public CitaServicio() {
    }

    // Constructor con parámetros
    public CitaServicio(Integer citaEnIntermedio, Integer servicioEnIntermedio, Integer cantidadSer) {
        this.citaEnIntermedio = citaEnIntermedio;
        this.servicioEnIntermedio = servicioEnIntermedio;
        this.cantidadSer = cantidadSer;
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

    public Integer getCantidadSer() {
        return cantidadSer;
    }

    public void setCantidadSer(Integer cantidadSer) {
        this.cantidadSer = cantidadSer;
    }

    public Citas getCita() {
        return cita;
    }

    public void setCita(Citas cita) {
        this.cita = cita;
    }

    public Servicios getServicio() {
        return servicio;
    }

    public void setServicio(Servicios servicio) {
        this.servicio = servicio;
    }
}
