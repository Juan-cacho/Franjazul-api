package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USUARIOS")
public class Usuarios {

    @Id
    @Column(name = "ID_USUARIO", length = 10)
    private String idUsuario;

    @Column(name = "NOMBRE_US", nullable = false, length = 70)
    private String nombreUs;

    @Column(name = "APELLIDO_US", nullable = false, length = 50)
    private String apellidoUs;

    @Column(name = "APELLIDO2_US", length = 50)
    private String apellido2Us;

    @Column(name = "EMAIL_US", nullable = false, length = 100)
    private String emailUs;

    @Column(name = "PASSWORD_US", nullable = false, length = 100)
    private String passwordUs;

    @Column(name = "TELEFONO_US", nullable = false)
    private Long telefonoUs;

    @ManyToOne
    @JoinColumn(name = "PERFIL_DE_USUARIO", nullable = false)
    private Perfiles perfilDeUsuario;

    @ManyToOne
    @JoinColumn(name = "CARGO_DE_USUARIO", nullable = false)
    private Cargos cargoDeUsuario;

    // Constructor vacío
    public Usuarios() {
    }

    // Constructor con parámetros
    public Usuarios(String idUsuario, String nombreUs, String apellidoUs, String emailUs,
                   String passwordUs, Long telefonoUs) {
        this.idUsuario = idUsuario;
        this.nombreUs = nombreUs;
        this.apellidoUs = apellidoUs;
        this.emailUs = emailUs;
        this.passwordUs = passwordUs;
        this.telefonoUs = telefonoUs;
    }

    public Usuarios(String idUsuario, String nombreUs, String apellidoUs, String apellido2Us, String emailUs, String passwordUs, Long telefonoUs, Perfiles perfilDeUsuario, Cargos cargoDeUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUs = nombreUs;
        this.apellidoUs = apellidoUs;
        this.apellido2Us = apellido2Us;
        this.emailUs = emailUs;
        this.passwordUs = passwordUs;
        this.telefonoUs = telefonoUs;
        this.perfilDeUsuario = perfilDeUsuario;
        this.cargoDeUsuario = cargoDeUsuario;
    }

    // Getters y Setters
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

    public Perfiles getPerfilDeUsuario() {
        return perfilDeUsuario;
    }

    public void setPerfilDeUsuario(Perfiles perfilDeUsuario) {
        this.perfilDeUsuario = perfilDeUsuario;
    }

    public Cargos getCargoDeUsuario() {
        return cargoDeUsuario;
    }

    public void setCargoDeUsuario(Cargos cargoDeUsuario) {
        this.cargoDeUsuario = cargoDeUsuario;
    }
}