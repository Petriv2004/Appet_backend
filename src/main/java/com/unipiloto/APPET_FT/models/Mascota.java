package com.unipiloto.APPET_FT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="MASCOTAS")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_mascota;

    @Column
    private String nombre;

    @Column
    private String tipo;

    @Column
    private String especie;

    @Column
    private String raza;

    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha_nacimiento;

    @ManyToOne
    @JoinColumn(name = "ID_PROPIETARIO")
    @JsonBackReference(value = "MASCOTA-PROPIETARIO")
    private Propietario propietario;

    @OneToOne
    @JoinColumn(name = "ID_HISTORIAL", referencedColumnName = "id_historial")
    @JsonManagedReference(value = "MASCOTA-HISTORIAL")
    private Historial historial;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "MASCOTA-AGENDA")
    private List<Agenda> citas;

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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Historial getHistorial() {
        return historial;
    }

    public void setHistorial(Historial historial) {
        this.historial = historial;
    }

    public List<Agenda> getCitas() {
        return citas;
    }

    public void setCitas(List<Agenda> citas) {
        this.citas = citas;
    }
}
