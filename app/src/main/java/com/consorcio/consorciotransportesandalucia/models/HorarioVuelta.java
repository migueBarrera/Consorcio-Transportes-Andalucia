package com.consorcio.consorciotransportesandalucia.models;

import java.util.ArrayList;

/**
 * Created by migueBarreraBluumi on 16/01/2018.
 */

public class HorarioVuelta {
    private ArrayList<String> horas;

    public ArrayList<String> getHoras() { return this.horas; }

    public void setHoras(ArrayList<String> horas) { this.horas = horas; }

    private String frecuencia;

    public String getFrecuencia() { return this.frecuencia; }

    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    private String observaciones;

    public String getObservaciones() { return this.observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    private String demandahoras;

    public String getDemandahoras() { return this.demandahoras; }

    public void setDemandahoras(String demandahoras) { this.demandahoras = demandahoras; }
}
