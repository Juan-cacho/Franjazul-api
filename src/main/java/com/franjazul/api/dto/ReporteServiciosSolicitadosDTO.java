// ReporteServiciosSolicitadosDTO.java
package com.franjazul.api.dto;

public class ReporteServiciosSolicitadosDTO {
    private Integer idServicio;
    private String nombreServicio;
    private String descripcion;
    private String tipoServicio;
    private String molecula;
    private Long vecesSolicitado;
    private Long cantidadTotal;
    private Long citasCompletadas;
    private Long citasPendientes;
    private Double porcentajeCompletado;

    // Constructor vacío
    public ReporteServiciosSolicitadosDTO() {}

    // Getters y Setters
    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getMolecula() { return molecula; }
    public void setMolecula(String molecula) { this.molecula = molecula; }

    public Long getVecesSolicitado() { return vecesSolicitado; }
    public void setVecesSolicitado(Long vecesSolicitado) { this.vecesSolicitado = vecesSolicitado; }

    public Long getCantidadTotal() { return cantidadTotal; }
    public void setCantidadTotal(Long cantidadTotal) { this.cantidadTotal = cantidadTotal; }

    public Long getCitasCompletadas() { return citasCompletadas; }
    public void setCitasCompletadas(Long citasCompletadas) { this.citasCompletadas = citasCompletadas; }

    public Long getCitasPendientes() { return citasPendientes; }
    public void setCitasPendientes(Long citasPendientes) { this.citasPendientes = citasPendientes; }

    public Double getPorcentajeCompletado() { return porcentajeCompletado; }
    public void setPorcentajeCompletado(Double porcentajeCompletado) { this.porcentajeCompletado = porcentajeCompletado; }
}
