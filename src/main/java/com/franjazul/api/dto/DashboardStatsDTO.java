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

    // Constructor vacío
    public DashboardStatsDTO() {
    }

    // Constructor con todos los parámetros
    public DashboardStatsDTO(Long totalCitas, Long citasPendientes, Long citasCompletadas,
                             Long citasCanceladas, Long totalTecnicos, Long totalCertificados,
                             Long certificadosVencidos, Long certificadosVigentes) {
        this.totalCitas = totalCitas;
        this.citasPendientes = citasPendientes;
        this.citasCompletadas = citasCompletadas;
        this.citasCanceladas = citasCanceladas;
        this.totalTecnicos = totalTecnicos;
        this.totalCertificados = totalCertificados;
        this.certificadosVencidos = certificadosVencidos;
        this.certificadosVigentes = certificadosVigentes;
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
}
