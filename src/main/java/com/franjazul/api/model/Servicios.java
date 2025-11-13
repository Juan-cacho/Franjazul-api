package com.franjazul.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SERVICIOS")
public class Servicios {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servicios")
    @SequenceGenerator(name = "seq_servicios", sequenceName = "SEQ_SERVICIOS", allocationSize = 1)
    @Column(name = "ID_SERVICIO")
    private Integer idServicio;

    @Column(name = "NOMBRE_SER", nullable = false, length = 20)
    private String nombreSer;

    @Column(name = "DESCRIPCION_SER", nullable = false, length = 150)
    private String descripcionSer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TPS_EN_SER", referencedColumnName = "NOMBRE_TPS", nullable = false)
    private TipoServicio tipoServicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MOL_EN_SER", referencedColumnName = "NOMBRE_MOL", nullable = true)
    private Moleculas molecula;

    // Constructor vacío
    public Servicios() {
    }

    public Servicios(String nombreSer, String descripcionSer, TipoServicio tipoServicio, Moleculas molecula) {
        this.nombreSer = nombreSer;
        this.descripcionSer = descripcionSer;
        this.tipoServicio = tipoServicio;
        this.molecula = molecula;
    }

    // Constructor con parámetros
    public Servicios(Integer idServicio, String nombreSer, String descripcionSer, TipoServicio tipoServicio, Moleculas molecula) {
        this.idServicio = idServicio;
        this.nombreSer = nombreSer;
        this.descripcionSer = descripcionSer;
        this.tipoServicio = tipoServicio;
        this.molecula = molecula;
    }

    // Getters y Setters
    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreSer() {
        return nombreSer;
    }

    public void setNombreSer(String nombreSer) {
        this.nombreSer = nombreSer;
    }

    public String getDescripcionSer() {
        return descripcionSer;
    }

    public void setDescripcionSer(String descripcionSer) {
        this.descripcionSer = descripcionSer;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Moleculas getMolecula() {
        return molecula;
    }

    public void setMolecula(Moleculas molecula) {
        this.molecula = molecula;
    }
}
