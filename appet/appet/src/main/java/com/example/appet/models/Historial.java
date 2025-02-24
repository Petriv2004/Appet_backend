package com.example.appet.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="HISTORIAL")
public class Historial {

    @Id
    private Integer id_historial;

    @Column
    private String enferemedades;

    @Column
    private String medicinas;

    @Column
    private String sangre;

    @Column
    private int peso;

    @OneToOne(mappedBy = "historial")
    @JsonBackReference(value = "MASCOTA-HISTORIAL")
    private Mascota mascota;

    public Integer getId_historial() {
        return id_historial;
    }

    public void setId_historial(Integer id_historial) {
        this.id_historial = id_historial;
    }

    public String getEnferemedades() {
        return enferemedades;
    }

    public void setEnferemedades(String enferemedades) {
        this.enferemedades = enferemedades;
    }

    public String getMedicinas() {
        return medicinas;
    }

    public void setMedicinas(String medicinas) {
        this.medicinas = medicinas;
    }

    public String getSangre() {
        return sangre;
    }

    public void setSangre(String sangre) {
        this.sangre = sangre;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
