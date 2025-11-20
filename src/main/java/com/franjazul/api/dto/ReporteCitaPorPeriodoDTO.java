
package com.franjazul.api.dto;

import java.time.LocalDateTime;

public class ReporteCitaPorPeriodoDTO {
    private Integer idCita;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String cliente;
    private String emailCliente;
    private String tecnico;
    private String lugar;
    private String direccion;
    private String servicios;
    private String estado;
    private String observaciones;

    // Constructor vacío
    public ReporteCitaPorPeriodoDTO() {}

    // Constructor completo
    public ReporteCitaPorPeriodoDTO(Integer idCita, LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                    String cliente, String emailCliente, String tecnico, String lugar,
                                    String direccion, String servicios, String estado, String observaciones) {
        this.idCita = idCita;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cliente = cliente;
        this.emailCliente = emailCliente;
        this.tecnico = tecnico;
        this.lugar = lugar;
        this.direccion = direccion;
        this.servicios = servicios;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getServicios() { return servicios; }
    public void setServicios(String servicios) { this.servicios = servicios; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
