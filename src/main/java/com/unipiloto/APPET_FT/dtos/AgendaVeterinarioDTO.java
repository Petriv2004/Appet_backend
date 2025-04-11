package com.unipiloto.APPET_FT.dtos;

import java.sql.Date;
import java.sql.Time;

public class AgendaVeterinarioDTO {
    private Integer id_agenda;
    private Date fecha;
    private Time hora;
    private String razon;
    private String descripcion;
    private boolean asistencia;
    private String nombre;

    public AgendaVeterinarioDTO(){

    }
    
    public AgendaVeterinarioDTO(Integer id_agenda, Date fecha, Time hora, String razon, String descripcion,
            boolean asistencia, String nombre) {
        this.id_agenda = id_agenda;
        this.fecha = fecha;
        this.hora = hora;
        this.razon = razon;
        this.descripcion = descripcion;
        this.asistencia = asistencia;
        this.nombre = nombre;
    }

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
