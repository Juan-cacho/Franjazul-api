package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TIPO_SERVICIO")
public class TipoServicio {

    @Id
    @Column(name = "NOMBRE_TPS", length = 30)
    private String nombreTps;

    @Column(name = "DESCRIPCION_TSP", nullable = false, length = 100)
    private String descripcionTsp;

    // Constructor vacío
    public TipoServicio() {
    }

    // Constructor con parámetros
    public TipoServicio(String nombreTps, String descripcionTsp) {
        this.nombreTps = nombreTps;
        this.descripcionTsp = descripcionTsp;
    }

    // Getters y Setters
    public String getNombreTps() {
        return nombreTps;
    }

    public void setNombreTps(String nombreTps) {
        this.nombreTps = nombreTps;
    }

    public String getDescripcionTsp() {
        return descripcionTsp;
    }

    public void setDescripcionTsp(String descripcionTsp) {
        this.descripcionTsp = descripcionTsp;
    }
}
