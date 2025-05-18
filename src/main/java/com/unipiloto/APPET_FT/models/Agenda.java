package com.unipiloto.APPET_FT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.sql.Time;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AGENDA")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_agenda;

    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column
    @Temporal(TemporalType.TIME)
    private Time hora;

    @Column
    private String razon;

    @Column
    private String descripcion;

    @Column
    private boolean estado;

    @Column
    private boolean asistencia;

    @ManyToOne
    @JsonBackReference(value = "MASCOTA-AGENDA")
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    public Integer getId_agenda() {
        return id_agenda;
    }

    public void setId_agenda(Integer id_agenda) {
        this.id_agenda = id_agenda;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }    

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
