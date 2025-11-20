
package com.franjazul.api.dto;

public class ReporteProductividadTecnicoDTO {
    private String idTecnico;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private Long totalCitasAsignadas;
    private Long citasCompletadas;
    private Long citasPendientes;
    private Long citasCanceladas;
    private Long citasReagendadas;
    private Double porcentajeEfectividad;
    private Double porcentajeCancelacion;

    // Constructor vacío
    public ReporteProductividadTecnicoDTO() {}

    // Getters y Setters
    public String getIdTecnico() { return idTecnico; }
    public void setIdTecnico(String idTecnico) { this.idTecnico = idTecnico; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Long getTotalCitasAsignadas() { return totalCitasAsignadas; }
    public void setTotalCitasAsignadas(Long totalCitasAsignadas) { this.totalCitasAsignadas = totalCitasAsignadas; }

    public Long getCitasCompletadas() { return citasCompletadas; }
    public void setCitasCompletadas(Long citasCompletadas) { this.citasCompletadas = citasCompletadas; }

    public Long getCitasPendientes() { return citasPendientes; }
    public void setCitasPendientes(Long citasPendientes) { this.citasPendientes = citasPendientes; }

    public Long getCitasCanceladas() { return citasCanceladas; }
    public void setCitasCanceladas(Long citasCanceladas) { this.citasCanceladas = citasCanceladas; }

    public Long getCitasReagendadas() { return citasReagendadas; }
    public void setCitasReagendadas(Long citasReagendadas) { this.citasReagendadas = citasReagendadas; }

    public Double getPorcentajeEfectividad() { return porcentajeEfectividad; }
    public void setPorcentajeEfectividad(Double porcentajeEfectividad) { this.porcentajeEfectividad = porcentajeEfectividad; }

    public Double getPorcentajeCancelacion() { return porcentajeCancelacion; }
    public void setPorcentajeCancelacion(Double porcentajeCancelacion) { this.porcentajeCancelacion = porcentajeCancelacion; }
}
