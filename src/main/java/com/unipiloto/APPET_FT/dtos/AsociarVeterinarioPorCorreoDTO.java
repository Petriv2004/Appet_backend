package com.unipiloto.APPET_FT.dtos;

public class AsociarVeterinarioPorCorreoDTO {
    private String correoPropietario;
    private String correoVeterinario;

    public AsociarVeterinarioPorCorreoDTO(String correoPropietario, String correoVeterinario) {
        this.correoPropietario = correoPropietario;
        this.correoVeterinario = correoVeterinario;
    }

    public AsociarVeterinarioPorCorreoDTO() {
    }

    public String getCorreoPropietario() {return correoPropietario;}

    public void setCorreoPropietario(String correoPropietario) {this.correoPropietario = correoPropietario;}

    public String getCorreoVeterinario() {return correoVeterinario;}

    public void setCorreoVeterinario(String correoVeterinario) {this.correoVeterinario = correoVeterinario;}
}
