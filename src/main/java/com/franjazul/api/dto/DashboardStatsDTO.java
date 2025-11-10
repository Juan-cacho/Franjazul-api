package com.franjazul.api.dto;

public class DashboardStatsDTO {

    private Long totalCitas;
    private Long citasPendientes;
    private Long citasCompletadas;
    private Long citasCanceladas;
    private Long totalTecnicos;
    private Long totalCertificados;
    private Long certificadosVencidos;
    private Long certificadosVigentes;
    private Double porcentajeCompletadas;
    private Double variacionMesAnterior;
    private Long serviciosActivos;

    // Constructor vacío
    public DashboardStatsDTO() {
    }

    // Getters y Setters
    public Long getTotalCitas() {
        return totalCitas;
    }

    public void setTotalCitas(Long totalCitas) {
        this.totalCitas = totalCitas;
    }

    public Long getCitasPendientes() {
        return citasPendientes;
    }

    public void setCitasPendientes(Long citasPendientes) {
        this.citasPendientes = citasPendientes;
    }

    public Long getCitasCompletadas() {
        return citasCompletadas;
    }

    public void setCitasCompletadas(Long citasCompletadas) {
        this.citasCompletadas = citasCompletadas;
    }

    public Long getCitasCanceladas() {
        return citasCanceladas;
    }

    public void setCitasCanceladas(Long citasCanceladas) {
        this.citasCanceladas = citasCanceladas;
    }

    public Long getTotalTecnicos() {
        return totalTecnicos;
    }

    public void setTotalTecnicos(Long totalTecnicos) {
        this.totalTecnicos = totalTecnicos;
    }

    public Long getTotalCertificados() {
        return totalCertificados;
    }

    public void setTotalCertificados(Long totalCertificados) {
        this.totalCertificados = totalCertificados;
    }

    public Long getCertificadosVencidos() {
        return certificadosVencidos;
    }

    public void setCertificadosVencidos(Long certificadosVencidos) {
        this.certificadosVencidos = certificadosVencidos;
    }

    public Long getCertificadosVigentes() {
        return certificadosVigentes;
    }

    public void setCertificadosVigentes(Long certificadosVigentes) {
        this.certificadosVigentes = certificadosVigentes;
    }

    public Double getPorcentajeCompletadas() {
        return porcentajeCompletadas;
    }

    public void setPorcentajeCompletadas(Double porcentajeCompletadas) {
        this.porcentajeCompletadas = porcentajeCompletadas;
    }

    public Double getVariacionMesAnterior() {
        return variacionMesAnterior;
    }

    public void setVariacionMesAnterior(Double variacionMesAnterior) {
        this.variacionMesAnterior = variacionMesAnterior;
    }

    public Long getServiciosActivos() {
        return serviciosActivos;
    }

    public void setServiciosActivos(Long serviciosActivos) {
        this.serviciosActivos = serviciosActivos;
    }
}
