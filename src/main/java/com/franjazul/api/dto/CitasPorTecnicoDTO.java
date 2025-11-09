package com.franjazul.api.dto;

public class CitasPorTecnicoDTO {

    private String idTecnico;
    private String nombreCompleto;
    private Long totalCitasAsignadas;
    private Long citasCompletadas;
    private Long citasPendientes;
    private Long citasCanceladas;
    private Double porcentajeEfectividad;

    // Constructor vacío
    public CitasPorTecnicoDTO() {
    }

    // Constructor con parámetros
    public CitasPorTecnicoDTO(String idTecnico, String nombreCompleto, Long totalCitasAsignadas,
                              Long citasCompletadas, Long citasPendientes, Long citasCanceladas,
                              Double porcentajeEfectividad) {
        this.idTecnico = idTecnico;
        this.nombreCompleto = nombreCompleto;
        this.totalCitasAsignadas = totalCitasAsignadas;
        this.citasCompletadas = citasCompletadas;
        this.citasPendientes = citasPendientes;
        this.citasCanceladas = citasCanceladas;
        this.porcentajeEfectividad = porcentajeEfectividad;
    }

    // Getters y Setters
    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Long getTotalCitasAsignadas() {
        return totalCitasAsignadas;
    }

    public void setTotalCitasAsignadas(Long totalCitasAsignadas) {
        this.totalCitasAsignadas = totalCitasAsignadas;
    }

    public Long getCitasCompletadas() {
        return citasCompletadas;
    }

    public void setCitasCompletadas(Long citasCompletadas) {
        this.citasCompletadas = citasCompletadas;
    }

    public Long getCitasPendientes() {
        return citasPendientes;
    }

    public void setCitasPendientes(Long citasPendientes) {
        this.citasPendientes = citasPendientes;
    }

    public Long getCitasCanceladas() {
        return citasCanceladas;
    }

    public void setCitasCanceladas(Long citasCanceladas) {
        this.citasCanceladas = citasCanceladas;
    }

    public Double getPorcentajeEfectividad() {
        return porcentajeEfectividad;
    }

    public void setPorcentajeEfectividad(Double porcentajeEfectividad) {
        this.porcentajeEfectividad = porcentajeEfectividad;
    }
}
