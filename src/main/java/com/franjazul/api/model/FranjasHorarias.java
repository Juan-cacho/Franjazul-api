package com.franjazul.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FRANJAS_HORARIAS")
public class FranjasHorarias {

    @Id
    @Column(name = "ID_FRANJA")
    private Integer idFranja;

    @Column(name = "FECHA_INICIO", nullable = false, columnDefinition = "DATE")
    private LocalDateTime fechaInicio;

    @Column(name = "FECHA_FIN", nullable = false, columnDefinition = "DATE")
    private LocalDateTime fechaFin;

    // Constructor vacío
    public FranjasHorarias() {
    }

    // Constructor con parámetros
    public FranjasHorarias(Integer idFranja, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.idFranja = idFranja;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    public Integer getIdFranja() {
        return idFranja;
    }

    public void setIdFranja(Integer idFranja) {
        this.idFranja = idFranja;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}
