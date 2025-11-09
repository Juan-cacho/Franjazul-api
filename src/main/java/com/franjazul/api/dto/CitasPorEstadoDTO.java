package com.franjazul.api.dto;

public class CitasPorEstadoDTO {

    private String estado;
    private String descripcion;
    private Long cantidad;

    // Constructor vacío
    public CitasPorEstadoDTO() {
    }

    // Constructor con parámetros
    public CitasPorEstadoDTO(String estado, String descripcion, Long cantidad) {
        this.estado = estado;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
