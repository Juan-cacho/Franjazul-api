package com.franjazul.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SolicitudCitaRequest {

    private String idUsuarioCliente;
    private List<Integer> serviciosIds;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String nombreLugar;
    private String direccionLugar;
    private Integer idTipoLugar;
    private Integer idLugarPadre;

    public SolicitudCitaRequest() {
    }

    public SolicitudCitaRequest(String idUsuarioCliente, List<Integer> serviciosIds,
                                LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                String nombreLugar, String direccionLugar,
                                Integer idTipoLugar, Integer idLugarPadre) {
        this.idUsuarioCliente = idUsuarioCliente;
        this.serviciosIds = serviciosIds;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombreLugar = nombreLugar;
        this.direccionLugar = direccionLugar;
        this.idTipoLugar = idTipoLugar;
        this.idLugarPadre = idLugarPadre;
    }

    public String getIdUsuarioCliente() {
        return idUsuarioCliente;
    }

    public void setIdUsuarioCliente(String idUsuarioCliente) {
        this.idUsuarioCliente = idUsuarioCliente;
    }

    public List<Integer> getServiciosIds() {
        return serviciosIds;
    }

    public void setServiciosIds(List<Integer> serviciosIds) {
        this.serviciosIds = serviciosIds;
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

    public Integer getIdTipoLugar() {
        return idTipoLugar;
    }

    public void setIdTipoLugar(Integer idTipoLugar) {
        this.idTipoLugar = idTipoLugar;
    }

    public Integer getIdLugarPadre() {
        return idLugarPadre;
    }

    public void setIdLugarPadre(Integer idLugarPadre) {
        this.idLugarPadre = idLugarPadre;
    }
}
