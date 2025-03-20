package com.unipiloto.APPET_FT.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="PROPIETARIOS")
public class Propietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_propietario;

    @Column
    private String nombre;

    @Column(unique = true)
    private String correo;

    @Column
    private String celular;

    @Column
    private String contrasena;

    @Column
    private String direccion;

    @Column
    private String genero;

    @Column
    private String rol;

    @OneToMany(mappedBy = "propietario")
    @JsonManagedReference(value = "MASCOTA-PROPIETARIO")
    private List<Mascota> macotasList;

    @OneToMany(mappedBy = "propietario")
    @JsonManagedReference(value = "EJERCICIO-PROPIETARIO")
    private List<Ejercicio> ejercicioslist;

    @ManyToMany
    @JoinTable(
            name = "PROPIETARIO_VETERINARIO",
            joinColumns = @JoinColumn(name = "id_propietario"),
            inverseJoinColumns = @JoinColumn(name = "id_veterinario")
    )
    private Set<Propietario> veterinarios;

    public Integer getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(Integer id_propietario) {
        this.id_propietario = id_propietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRol() {return rol;}

    public void setRol(String rol) {this.rol = rol;}

    public List<Mascota> getMacotasList() {
        return macotasList;
    }

    public void setMacotasList(List<Mascota> macotasList) {
        this.macotasList = macotasList;
    }

    public List<Ejercicio> getEjercicioslist() {return ejercicioslist;}

    public void setEjercicioslist(List<Ejercicio> ejercicioslist) {this.ejercicioslist = ejercicioslist;}

    public Set<Propietario> getVeterinarios() {return veterinarios;}

    public void setVeterinarios(Set<Propietario> veterinarios) {this.veterinarios = veterinarios;}
}
