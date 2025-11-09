package com.franjazul.api.dto;

import java.time.LocalDateTime;

public class CitaDetalleDTO {

    private Integer idCita;
    private String observaciones;
    private String estado;
    private String descripcionEstado;
    private String lugar;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String usuarioCreo;

    // Constructor vacío
    public CitaDetalleDTO() {
    }

    // Constructor con parámetros
    public CitaDetalleDTO(Integer idCita, String observaciones, String estado,
                          String descripcionEstado, String lugar, LocalDateTime fechaInicio,
                          LocalDateTime fechaFin, String usuarioCreo) {
        this.idCita = idCita;
        this.observaciones = observaciones;
        this.estado = estado;
        this.descripcionEstado = descripcionEstado;
        this.lugar = lugar;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuarioCreo = usuarioCreo;
    }

    // Getters y Setters
    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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

    public String getUsuarioCreo() {
        return usuarioCreo;
    }

    public void setUsuarioCreo(String usuarioCreo) {
        this.usuarioCreo = usuarioCreo;
    }
}
