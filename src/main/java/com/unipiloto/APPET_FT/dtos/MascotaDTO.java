package com.unipiloto.APPET_FT.dtos;

public class MascotaDTO {
    private String tipo;
    private String tamanio;
    private int ritmoCardiaco;

    public MascotaDTO(String tipo, String tamanio, int ritmoCardiaco) {
        this.tipo = tipo;
        this.tamanio = tamanio;
        this.ritmoCardiaco = ritmoCardiaco;
    }

    public MascotaDTO() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public int getRitmoCardiaco() {
        return ritmoCardiaco;
    }

    public void setRitmoCardiaco(int ritmoCardiaco) {
        this.ritmoCardiaco = ritmoCardiaco;
    }
}
