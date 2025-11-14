package com.franjazul.api.dto;

public class LoginResponse {

    private String token;
    private String idUsuario;
    private String nombreCompleto;
    private String email;
    private String cargo;
    private Integer idPerfil;
    private String nombrePerfil;

    public LoginResponse() {
    }

    public LoginResponse(String token, String idUsuario, String nombreCompleto,
                         String email, String cargo, Integer idPerfil, String nombrePerfil) {
        this.token = token;
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.cargo = cargo;
        this.idPerfil = idPerfil;
        this.nombrePerfil = nombrePerfil;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }
}