package com.consorcio.consorciotransportesandalucia.models;

/**
 * Created by migueBarreraBluumi on 15/01/2018.
 */

public class Nucleo {
    private String idNucleo;

    public String getIdNucleo() { return this.idNucleo; }

    public void setIdNucleo(String idNucleo) { this.idNucleo = idNucleo; }

    private String idMunicipio;

    public String getIdMunicipio() { return this.idMunicipio; }

    public void setIdMunicipio(String idMunicipio) { this.idMunicipio = idMunicipio; }

    private String idZona;

    public String getIdZona() { return this.idZona; }

    public void setIdZona(String idZona) { this.idZona = idZona; }

    private int colspan;

    public int getColspan() { return this.colspan; }

    public void setColspan(int colspan) { this.colspan = colspan; }

    private String nombre;

    public String getNombre() { return this.nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    private String color;

    public String getColor() { return this.color; }

    public void setColor(String color) { this.color = color; }

    @Override
    public String toString() {
        return getNombre();
    }
}
