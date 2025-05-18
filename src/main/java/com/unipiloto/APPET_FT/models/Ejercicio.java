package com.unipiloto.APPET_FT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="EJERCICIOS")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ejercicio;

    @Column
    private String nombre;

    @Column
    private int duracion;

    @Column
    private String intensidad;

    @Column
    private String especie;

    @Column
    private int tiempo_descanso;

    @Column(columnDefinition = "TEXT")
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "ID_PROPIETARIO")
    @JsonBackReference(value = "EJERCICIO-PROPIETARIO")
    private Propietario propietario;

    public Integer getId_ejercicio() {return id_ejercicio;}

    public void setId_ejercicio(Integer id_ejercicio) {this.id_ejercicio = id_ejercicio;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public int getDuracion() {return duracion;}

    public void setDuracion(int duracion) {this.duracion = duracion;}

    public String getIntensidad() {return intensidad;}

    public void setIntensidad(String intensidad) {this.intensidad = intensidad;}

    public String getEspecie() {return especie;}

    public void setEspecie(String especie) {this.especie = especie;}

    public int getTiempo_descanso() {return tiempo_descanso;}

    public void setTiempo_descanso(int tiempo_descanso) {this.tiempo_descanso = tiempo_descanso;}

    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen;}

    public Propietario getPropietario() {return propietario;}

    public void setPropietario(Propietario propietario) {this.propietario = propietario;}
}
