package com.consorcio.consorciotransportesandalucia.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by migueBarreraBluumi on 24/01/2018.
 */

public class MarkerInfo {

    private String title;
    private LatLng pos;
    private String nucleo;

    public MarkerInfo(){}

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
