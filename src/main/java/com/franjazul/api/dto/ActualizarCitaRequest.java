package com.franjazul.api.dto;

import java.time.LocalDateTime;

public class ActualizarCitaRequest {

    private Integer idCita;
    private String observacionesCita;
    private String estadoCita;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public ActualizarCitaRequest() {
    }

    public ActualizarCitaRequest(Integer idCita, String observacionesCita, String estadoCita,
                                 LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.idCita = idCita;
        this.observacionesCita = observacionesCita;
        this.estadoCita = estadoCita;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

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

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
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
