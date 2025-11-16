package com.franjazul.api.dto;

public class RegistroRequest {

    private String idUsuario;
    private String nombreUs;
    private String apellidoUs;
    private String apellido2Us;
    private String emailUs;
    private String passwordUs;
    private Long telefonoUs;

    public RegistroRequest() {
    }

    public RegistroRequest(String idUsuario, String nombreUs, String apellidoUs, String apellido2Us,
                           String emailUs, String passwordUs, Long telefonoUs) {
        this.idUsuario = idUsuario;
        this.nombreUs = nombreUs;
        this.apellidoUs = apellidoUs;
        this.apellido2Us = apellido2Us;
        this.emailUs = emailUs;
        this.passwordUs = passwordUs;
        this.telefonoUs = telefonoUs;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUs() {
        return nombreUs;
    }

    public void setNombreUs(String nombreUs) {
        this.nombreUs = nombreUs;
    }

    public String getApellidoUs() {
        return apellidoUs;
    }

    public void setApellidoUs(String apellidoUs) {
        this.apellidoUs = apellidoUs;
    }

    public String getApellido2Us() {
        return apellido2Us;
    }

    public void setApellido2Us(String apellido2Us) {
        this.apellido2Us = apellido2Us;
    }

    public String getEmailUs() {
        return emailUs;
    }

    public void setEmailUs(String emailUs) {
        this.emailUs = emailUs;
    }

    public String getPasswordUs() {
        return passwordUs;
    }

    public void setPasswordUs(String passwordUs) {
        this.passwordUs = passwordUs;
    }

    public Long getTelefonoUs() {
        return telefonoUs;
    }

    public void setTelefonoUs(Long telefonoUs) {
        this.telefonoUs = telefonoUs;
    }
}
