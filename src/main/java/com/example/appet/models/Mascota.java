package com.example.appet.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="MASCOTAS")
public class Mascota {

    @Id
    private Integer id_mascota;

    @Column
    private String nombre;

    @Column
    private String tipo;

    @Column
    private String raza;

    @Column
    private int edad;

    @ManyToOne
    @JoinColumn(name = "ID_PROPIETARIO")
    @JsonBackReference(value = "MASCOTA-PROPIETARIO")
    private Propietario id_propietario;

    @OneToOne
    @JoinColumn(name = "ID_HISTORIAL", referencedColumnName = "id_historial")
    @JsonManagedReference(value = "MASCOTA-HISTORIAL")
    private Historial historial;

    public Integer getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(Integer id_mascota) {
        this.id_mascota = id_mascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Propietario getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(Propietario id_propietario) {
        this.id_propietario = id_propietario;
    }

    public Historial getHistorial() {
        return historial;
    }

    public void setHistorial(Historial historial) {
        this.historial = historial;
    }
}
