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

    private String latitud;

    public String getLatitud() {
        return this.latitud;
    }

    public void setLatitud(String latitud) { this.latitud = latitud; }

    private String longitud;

    public String getLongitud() { return this.longitud; }

    public void setLongitud(String longitud) { this.longitud = longitud; }

    private String idMunicipio;

    public String getIdMunicipio() { return this.idMunicipio; }

    public void setIdMunicipio(String idMunicipio) { this.idMunicipio = idMunicipio; }

    private String municipio;

    public String getMunicipio() { return this.municipio; }

    public void setMunicipio(String municipio) { this.municipio = municipio; }

    private String nucleo;

    public String getNucleo() { return this.nucleo; }

    public void setNucleo(String nucleo) { this.nucleo = nucleo; }

    private String idLinea;

    public String getIdLinea() { return this.idLinea; }

    public void setIdLinea(String idLinea) { this.idLinea = idLinea; }

    private String sentido;

    public String getSentido() { return this.sentido; }

    public void setSentido(String sentido) { this.sentido = sentido; }

    private String orden;

    public String getOrden() { return this.orden; }

    public void setOrden(String orden) { this.orden = orden; }

    private String modos;

    public String getModos() { return this.modos; }

    public void setModos(String modos) { this.modos = modos; }

    @Override
    public LatLng getPosition() {
        Double lat = null;
        if(latitud == null || latitud.equals(""))
            lat = 0.0;
        else
            lat = Double.valueOf(getLatitud());

        Double lon = null;
        if(longitud == null || longitud.equals(""))
            lon = 0.0;
        else
            lon = Double.valueOf(getLongitud());

        return new LatLng(lat,lon);
    }
}
