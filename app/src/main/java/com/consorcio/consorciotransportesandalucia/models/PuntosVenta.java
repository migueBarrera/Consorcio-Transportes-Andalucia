package com.consorcio.consorciotransportesandalucia.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public class PuntosVenta implements ClusterItem{
    private String idComercio;

    public String getIdComercio() { return this.idComercio; }

    public void setIdComercio(String idComercio) { this.idComercio = idComercio; }

    private String idTipo;

    public String getIdTipo() { return this.idTipo; }

    public void setIdTipo(String idTipo) { this.idTipo = idTipo; }

    private String idNucleo;

    public String getIdNucleo() { return this.idNucleo; }

    public void setIdNucleo(String idNucleo) { this.idNucleo = idNucleo; }

    private String idMunicipio;

    public String getIdMunicipio() { return this.idMunicipio; }

    public void setIdMunicipio(String idMunicipio) { this.idMunicipio = idMunicipio; }

    public TipoPuntoVenta tipo;

    public TipoPuntoVenta getTipo() { return this.tipo; }

    public void setTipo(TipoPuntoVenta tipo) { this.tipo = tipo; }

    private String municipio;

    public String getMunicipio() { return this.municipio; }

    public void setMunicipio(String municipio) { this.municipio = municipio; }

    private String nucleo;

    public String getNucleo() { return this.nucleo; }

    public void setNucleo(String nucleo) { this.nucleo = nucleo; }

    private String direccion;

    public String getDireccion() { return this.direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    private double latitud;

    public double getLatitud() { return this.latitud; }

    public void setLatitud(double latitud) { this.latitud = latitud; }

    private double longitud;

    public double getLongitud() { return this.longitud; }

    public void setLongitud(double longitud) { this.longitud = longitud; }

    @Override
    public LatLng getPosition() {
        return new LatLng(getLatitud(),getLongitud());
    }
}
