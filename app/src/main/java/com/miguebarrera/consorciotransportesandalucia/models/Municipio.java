package com.miguebarrera.consorciotransportesandalucia.models;

/**
 * Created by migueBarreraBluumi on 15/01/2018.
 */

public class Municipio {
    private String idMunicipio;

    public String getIdMunicipio() { return this.idMunicipio; }

    public void setIdMunicipio(String idMunicipio) { this.idMunicipio = idMunicipio; }

    private String datos;

    public String getDatos() { return this.datos; }

    public void setDatos(String datos) { this.datos = datos; }

    @Override
    public String toString() {
        return getDatos();
    }
}
