package com.unipiloto.APPET_FT.dtos;

public class UbicacionDTO {
    private double latitud;
    private double longitud;

    public UbicacionDTO(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public UbicacionDTO() {
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
