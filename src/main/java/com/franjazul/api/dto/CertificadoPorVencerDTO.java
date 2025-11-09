package com.franjazul.api.dto;

import java.time.LocalDateTime;

public class CertificadoPorVencerDTO {

    private String codigoCertificado;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaVencimiento;
    private Integer diasParaVencer;
    private Integer idCita;
    private String lugar;
    private String tecnico;

    // Constructor vacío
    public CertificadoPorVencerDTO() {
    }

    // Constructor con parámetros
    public CertificadoPorVencerDTO(String codigoCertificado, LocalDateTime fechaEmision,
                                   LocalDateTime fechaVencimiento, Integer diasParaVencer,
                                   Integer idCita, String lugar, String tecnico) {
        this.codigoCertificado = codigoCertificado;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.diasParaVencer = diasParaVencer;
        this.idCita = idCita;
        this.lugar = lugar;
        this.tecnico = tecnico;
    }

    // Getters y Setters
    public String getCodigoCertificado() {
        return codigoCertificado;
    }

    public void setCodigoCertificado(String codigoCertificado) {
        this.codigoCertificado = codigoCertificado;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getDiasParaVencer() {
        return diasParaVencer;
    }

    public void setDiasParaVencer(Integer diasParaVencer) {
        this.diasParaVencer = diasParaVencer;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }
}
