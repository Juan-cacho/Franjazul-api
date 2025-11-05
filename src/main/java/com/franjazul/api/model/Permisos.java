package com.franjazul.api.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERMISOS")
@IdClass(PermisoId.class)
public class Permisos implements Serializable {

    @Id
    @Column(name = "ID_PER_EN_PERM")
    private Integer idPerEnPerm;

    @Id
    @Column(name = "ID_FORM_EN_PERM")
    private Integer idFormEnPerm;

    @ManyToOne
    @JoinColumn(name = "ID_PER_EN_PERM", insertable = false, updatable = false)
    private Perfiles perfil;

    @ManyToOne
    @JoinColumn(name = "ID_FORM_EN_PERM", insertable = false, updatable = false)
    private Formularios formulario;

    @Column(name = "PUEDE_CREAR", nullable = false)
    private Integer puedeCrear;

    @Column(name = "PUEDE_BORRAR", nullable = false)
    private Integer puedeBorrar;

    @Column(name = "PUEDE_EDITAR", nullable = false)
    private Integer puedeEditar;

    @Column(name = "PUEDE_LEER", nullable = false)
    private Integer puedeLeer;

    // Constructor vacío
    public Permisos() {
    }

    // Constructor con parámetros
    public Permisos(Integer idPerEnPerm, Integer idFormEnPerm, Integer puedeCrear,
                   Integer puedeBorrar, Integer puedeEditar, Integer puedeLeer) {
        this.idPerEnPerm = idPerEnPerm;
        this.idFormEnPerm = idFormEnPerm;
        this.puedeCrear = puedeCrear;
        this.puedeBorrar = puedeBorrar;
        this.puedeEditar = puedeEditar;
        this.puedeLeer = puedeLeer;
    }

    // Getters y Setters
    public Integer getIdPerEnPerm() {
        return idPerEnPerm;
    }

    public void setIdPerEnPerm(Integer idPerEnPerm) {
        this.idPerEnPerm = idPerEnPerm;
    }

    public Integer getIdFormEnPerm() {
        return idFormEnPerm;
    }

    public void setIdFormEnPerm(Integer idFormEnPerm) {
        this.idFormEnPerm = idFormEnPerm;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public Formularios getFormulario() {
        return formulario;
    }

    public void setFormulario(Formularios formulario) {
        this.formulario = formulario;
    }

    public Integer getPuedeCrear() {
        return puedeCrear;
    }

    public void setPuedeCrear(Integer puedeCrear) {
        this.puedeCrear = puedeCrear;
    }

    public Integer getPuedeBorrar() {
        return puedeBorrar;
    }

    public void setPuedeBorrar(Integer puedeBorrar) {
        this.puedeBorrar = puedeBorrar;
    }

    public Integer getPuedeEditar() {
        return puedeEditar;
    }

    public void setPuedeEditar(Integer puedeEditar) {
        this.puedeEditar = puedeEditar;
    }

    public Integer getPuedeLeer() {
        return puedeLeer;
    }

    public void setPuedeLeer(Integer puedeLeer) {
        this.puedeLeer = puedeLeer;
    }
}