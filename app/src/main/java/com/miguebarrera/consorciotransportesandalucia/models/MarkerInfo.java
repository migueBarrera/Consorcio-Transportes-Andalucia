package com.miguebarrera.consorciotransportesandalucia.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by migueBarreraBluumi on 24/01/2018.
 */

public class MarkerInfo {

    private String idParada;
    private String title;
    private LatLng pos;
    private String nucleo;
    private TipoPuntoVenta tipoPuntoVenta;

    public MarkerInfo(){}

    public TipoPuntoVenta getTipoPuntoVenta() {
        return tipoPuntoVenta;
    }

    public void setTipoPuntoVenta(TipoPuntoVenta tipoPuntoVenta) {
        this.tipoPuntoVenta = tipoPuntoVenta;
    }

    public String getIdParada() {
        return idParada;
    }

    public void setIdParada(String idParada) {
        this.idParada = idParada;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getPos() {
        return pos;
    }

    public void setPos(LatLng pos) {
        this.pos = pos;
    }

    public String getNucleo() {
        return nucleo;
    }

    public void setNucleo(String nucleo) {
        this.nucleo = nucleo;
    }
}
