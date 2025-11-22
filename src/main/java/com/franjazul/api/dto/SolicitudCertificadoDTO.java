package com.franjazul.api.dto;

import lombok.Data;

@Data
public class SolicitudCertificadoDTO {
    private String tipoDocumento; // "CC" o "NIT"

    // Para CC
    private String cedula;

    // Para NIT
    private String nit;
    private String nombreEmpresa;
    private String rut;
    private String cedulaSolicitante; // CC de quien solicita (para validar con token)

    public SolicitudCertificadoDTO() {
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getCedulaSolicitante() {
        return cedulaSolicitante;
    }

    public void setCedulaSolicitante(String cedulaSolicitante) {
        this.cedulaSolicitante = cedulaSolicitante;
    }
}