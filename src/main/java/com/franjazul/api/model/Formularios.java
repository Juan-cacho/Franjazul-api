package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FORMULARIOS")
public class Formularios {

    @Id
    @Column(name = "ID_FORM")
    private Integer idForm;

    @Column(name = "TITULO_FORM", nullable = false, length = 100)
    private String tituloForm;

    @Column(name = "URL_FORM", nullable = false, length = 100)
    private String urlForm;

    @Column(name = "ES_PADRE", nullable = false)
    private Integer esPadre;

    @Column(name = "ORDEN", nullable = false)
    private Integer orden;

    @ManyToOne
    @JoinColumn(name = "FORM_RECURSIVO")
    private Formularios formRecursivo;

    // Constructor vacío
    public Formularios() {
    }

    // Constructor con parámetros
    public Formularios(Integer idForm, String tituloForm, String urlForm, Integer esPadre, Integer orden) {
        this.idForm = idForm;
        this.tituloForm = tituloForm;
        this.urlForm = urlForm;
        this.esPadre = esPadre;
        this.orden = orden;
    }

    // Getters y Setters
    public Integer getIdForm() {
        return idForm;
    }

    public void setIdForm(Integer idForm) {
        this.idForm = idForm;
    }

    public String getTituloForm() {
        return tituloForm;
    }

    public void setTituloForm(String tituloForm) {
        this.tituloForm = tituloForm;
    }

    public String getUrlForm() {
        return urlForm;
    }

    public void setUrlForm(String urlForm) {
        this.urlForm = urlForm;
    }

    public Integer getEsPadre() {
        return esPadre;
    }

    public void setEsPadre(Integer esPadre) {
        this.esPadre = esPadre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Formularios getFormRecursivo() {
        return formRecursivo;
    }

    public void setFormRecursivo(Formularios formRecursivo) {
        this.formRecursivo = formRecursivo;
    }
}
