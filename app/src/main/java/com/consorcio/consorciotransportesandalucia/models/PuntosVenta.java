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

    public String tipo;

    public TipoPuntoVenta getTipo() {
        TipoPuntoVenta var = null;

        if (tipo.equals("Comercio") || tipo.equals("COMERCIO") || tipo.equals(""))
            var = TipoPuntoVenta.Comercio;
        else if (tipo.equals("Estanco") || tipo.equals("ESTANCO"))
            var = TipoPuntoVenta.Estanco;
        else if (tipo.equals("Kiosco") || tipo.equals("KIOSKO") || tipo.equals("Kiosko"))
            var = TipoPuntoVenta.Kiosco;
        else if (tipo.equals("Papelería"))
            var = TipoPuntoVenta.Papelería;
        else if (tipo.equals("Taquilla"))
            var = TipoPuntoVenta.Taquilla;
        else if (tipo.equals("Copistería"))
            var = TipoPuntoVenta.Copistería;
        else if (tipo.equals("Kiosko Librería"))
            var = TipoPuntoVenta.Kiosko_Librería;
        else if (tipo.equals("Tienda"))
            var = TipoPuntoVenta.Tienda;
        else if (tipo.equals("Prensa"))
            var = TipoPuntoVenta.Prensa;
        else if (tipo.equals("Bar"))
            var = TipoPuntoVenta.Bar;
        else if (tipo.equals("Información"))
            var = TipoPuntoVenta.Información;
        else if (tipo.equals("Alimentación"))
            var = TipoPuntoVenta.Alimentación;
        else if (tipo.equals("Metro") || tipo.equals("CTMAS"))
            var = TipoPuntoVenta.Metro;
        else if (tipo.equals("OTROS"))
            var = TipoPuntoVenta.Default;
        else
            var = TipoPuntoVenta.Default;

        return var;
    }

    public void setTipo(String tipo) { this.tipo = tipo; }

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
