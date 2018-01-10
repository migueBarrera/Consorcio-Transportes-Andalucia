package com.consorcio.consorciotransportesandalucia.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public class Parada implements ClusterItem {


    private String idParada;

    public String getIdParada() { return this.idParada; }

    public void setIdParada(String idParada) { this.idParada = idParada; }

    private String idNucleo;

    public String getIdNucleo() { return this.idNucleo; }

    public void setIdNucleo(String idNucleo) { this.idNucleo = idNucleo; }

    private String idZona;

    public String getIdZona() { return this.idZona; }

    public void setIdZona(String idZona) { this.idZona = idZona; }

    private String nombre;

    public String getNombre() { return this.nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    private double latitud;

    public double getLatitud() { return this.latitud; }

    public void setLatitud(double latitud) { this.latitud = latitud; }

    private double longitud;

    public double getLongitud() { return this.longitud; }

    public void setLongitud(double longitud) { this.longitud = longitud; }

    private String idMunicipio;

    public String getIdMunicipio() { return this.idMunicipio; }

    public void setIdMunicipio(String idMunicipio) { this.idMunicipio = idMunicipio; }

    private String municipio;

    public String getMunicipio() { return this.municipio; }

    public void setMunicipio(String municipio) { this.municipio = municipio; }

    private String nucleo;

    public String getNucleo() { return this.nucleo; }

    public void setNucleo(String nucleo) { this.nucleo = nucleo; }

    @Override
    public LatLng getPosition() {
        return new LatLng(getLatitud(),getLongitud());
    }
}
