package com.franjazul.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MisCitaDetalleDTO {

    private Integer idCita;
    private String observacionesCita;
    private String nombreCliente;
    private String idCliente;
    private String nombreTecnico;
    private String idTecnico;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String nombreLugar;
    private String direccionLugar;
    private String estadoCita;
    private String descripcionEstado;
    private List<String> servicios;

    public MisCitaDetalleDTO() {
    }

    public MisCitaDetalleDTO(Integer idCita, String observacionesCita, String nombreCliente,
                          String idCliente, String nombreTecnico, String idTecnico,
                          LocalDateTime fechaInicio, LocalDateTime fechaFin,
                          String nombreLugar, String direccionLugar,
                          String estadoCita, String descripcionEstado, List<String> servicios) {
        this.idCita = idCita;
        this.observacionesCita = observacionesCita;
        this.nombreCliente = nombreCliente;
        this.idCliente = idCliente;
        this.nombreTecnico = nombreTecnico;
        this.idTecnico = idTecnico;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombreLugar = nombreLugar;
        this.direccionLugar = direccionLugar;
        this.estadoCita = estadoCita;
        this.descripcionEstado = descripcionEstado;
        this.servicios = servicios;
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

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
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

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getDireccionLugar() {
        return direccionLugar;
    }

    public void setDireccionLugar(String direccionLugar) {
        this.direccionLugar = direccionLugar;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }
}
