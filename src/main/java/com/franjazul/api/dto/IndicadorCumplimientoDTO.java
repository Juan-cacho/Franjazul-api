package com.franjazul.api.dto;

public class IndicadorCumplimientoDTO {

    private String nombreServicio;
    private String tipoServicio;
    private Long totalVecesSolicitado;
    private Long completadas;
    private Double porcentajeCumplimiento;

    // Constructor vacío
    public IndicadorCumplimientoDTO() {
    }

    // Constructor con parámetros
    public IndicadorCumplimientoDTO(String nombreServicio, String tipoServicio,
                                    Long totalVecesSolicitado, Long completadas,
                                    Double porcentajeCumplimiento) {
        this.nombreServicio = nombreServicio;
        this.tipoServicio = tipoServicio;
        this.totalVecesSolicitado = totalVecesSolicitado;
        this.completadas = completadas;
        this.porcentajeCumplimiento = porcentajeCumplimiento;
    }

    // Getters y Setters
    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Long getTotalVecesSolicitado() {
        return totalVecesSolicitado;
    }

    public void setTotalVecesSolicitado(Long totalVecesSolicitado) {
        this.totalVecesSolicitado = totalVecesSolicitado;
    }

    public Long getCompletadas() {
        return completadas;
    }

    public void setCompletadas(Long completadas) {
        this.completadas = completadas;
    }

    public Double getPorcentajeCumplimiento() {
        return porcentajeCumplimiento;
    }

    public void setPorcentajeCumplimiento(Double porcentajeCumplimiento) {
        this.porcentajeCumplimiento = porcentajeCumplimiento;
    }
}
