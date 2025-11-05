package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CARGOS")
public class Cargos {

    @Id
    @Column(name = "NOMBRE_CARGO", length = 15)
    private String nombreCargo;

    @Column(name = "DESCRIPCION_CARGO", length = 30)
    private String descripcionCargo;

    public Cargos() {
    }

    public Cargos(String nombreCargo, String descripcionCargo) {
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }
}
