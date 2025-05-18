package com.unipiloto.APPET_FT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="RECORRIDOS")
public class Recorrido {

    public Recorrido(String latitud, String longitud, String rango) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.rango = rango;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_recorrido;

    @Column
    private String latitud;

    @Column
    private String longitud;

    @Column
    private String rango;

    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JsonBackReference(value = "MASCOTA-RECORRIDO")
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    public Integer getId_recorrido() {return id_recorrido;}

    public void setId_recorrido(Integer id_recorrido) {this.id_recorrido = id_recorrido;}

    public String getLatitud() {return latitud;}

    public void setLatitud(String latitud) {this.latitud = latitud;}

    public String getLongitud() {return longitud;}

    public void setLongitud(String longitud) {this.longitud = longitud;}

    public String getRango() {return rango;}

    public void setRango(String rango) {this.rango = rango;}

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {this.fecha = fecha;}

    public Mascota getMascota() {return mascota;}

    public void setMascota(Mascota mascota) {this.mascota = mascota;}
}
