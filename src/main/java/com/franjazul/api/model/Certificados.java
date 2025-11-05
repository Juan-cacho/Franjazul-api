package com.franjazul.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CERTIFICADOS")
public class Certificados {

    @Id
    @Column(name = "CODIGO_CER", length = 10)
    private String codigoCer;

    @Column(name = "FECHA_EMISION", nullable = false, columnDefinition = "DATE")
    private LocalDateTime fechaEmision;

    @Column(name = "FECHA_VENCE", nullable = false, columnDefinition = "DATE")
    private LocalDateTime fechaVence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CITA_EN_CER", referencedColumnName = "ID_CITA", nullable = false)
    private Citas cita;

    // Constructor vacío
    public Certificados() {
    }

    // Constructor con parámetros
    public Certificados(String codigoCer, LocalDateTime fechaEmision, LocalDateTime fechaVence, Citas cita) {
        this.codigoCer = codigoCer;
        this.fechaEmision = fechaEmision;
        this.fechaVence = fechaVence;
        this.cita = cita;
    }

    // Getters y Setters
    public String getCodigoCer() {
        return codigoCer;
    }

    public void setCodigoCer(String codigoCer) {
        this.codigoCer = codigoCer;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDateTime getFechaVence() {
        return fechaVence;
    }

    public void setFechaVence(LocalDateTime fechaVence) {
        this.fechaVence = fechaVence;
    }

    public Citas getCita() {
        return cita;
    }

    public void setCita(Citas cita) {
        this.cita = cita;
    }
}
