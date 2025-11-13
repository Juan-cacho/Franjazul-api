package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "LUGARES")
public class Lugares {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lugares")
    @SequenceGenerator(name = "seq_lugares", sequenceName = "SEQ_LUGARES", allocationSize = 1)
    @Column(name = "ID_LUGAR")
    private Integer idLugar;

    @Column(name = "NOMBRE_LUGAR", nullable = false, length = 100)
    private String nombreLugar;

    @Column(name = "DIRECCION_LUGAR", nullable = false, length = 200)
    private String direccionLugar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TL_DEL_LUGAR", referencedColumnName = "ID_TL", nullable = false)
    private TipoLugar tipoLugar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LUGAR_DENTRO_LUGAR", referencedColumnName = "ID_LUGAR", nullable = true)
    private Lugares lugarPadre;

    // Constructor vacío
    public Lugares() {
    }

    // Constructor SIN ID
    public Lugares(String nombreLugar, String direccionLugar, TipoLugar tipoLugar, Lugares lugarPadre) {
        this.nombreLugar = nombreLugar;
        this.direccionLugar = direccionLugar;
        this.tipoLugar = tipoLugar;
        this.lugarPadre = lugarPadre;
    }

    // Getters y Setters
    public Integer getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(Integer idLugar) {
        this.idLugar = idLugar;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getDireccionLugar() {
        return direccionLugar;
    }

    public void setDireccionLugar(String direccionLugar) {
        this.direccionLugar = direccionLugar;
    }

    public TipoLugar getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(TipoLugar tipoLugar) {
        this.tipoLugar = tipoLugar;
    }

    public Lugares getLugarPadre() {
        return lugarPadre;
    }

    public void setLugarPadre(Lugares lugarPadre) {
        this.lugarPadre = lugarPadre;
    }
}
