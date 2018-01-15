package com.consorcio.consorciotransportesandalucia.models;

import java.util.ArrayList;

/**
 * Created by migueBarreraBluumi on 15/01/2018.
 */

public class Horario {
    private String idlinea;

    public String getIdlinea() { return this.idlinea; }

    public void setIdlinea(String idlinea) { this.idlinea = idlinea; }

    private String codigo;

    public String getCodigo() { return this.codigo; }

    public void setCodigo(String codigo) { this.codigo = codigo; }

    private ArrayList<String> horas;

    public ArrayList<String> getHoras() { return this.horas; }

    public void setHoras(ArrayList<String> horas) { this.horas = horas; }

    private String dias;

    public String getDias() { return this.dias; }

    public void setDias(String dias) { this.dias = dias; }

    private String observaciones;

    public String getObservaciones() { return this.observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    private String demandahoras;

    public String getDemandahoras() { return this.demandahoras; }

    public void setDemandahoras(String demandahoras) { this.demandahoras = demandahoras; }
}
